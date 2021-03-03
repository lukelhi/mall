package com.mall.service.impl;

import com.mall.mapper.ContentCategoryMapper;
import com.mall.pojo.ContentCategory;
import com.mall.pojo.ContentCategoryExample;
import com.mall.pojo.EasyUITreeNode;
import com.mall.pojo.MallResult;
import com.mall.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private ContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCatList(Long parentId) {
		//根据parentId查询子节点列表
		ContentCategoryExample example = new ContentCategoryExample();
		ContentCategoryExample.Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		//执行查询
		List<ContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		//转换成EasyUITreeNode列表
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (ContentCategory contentCategory : list) {
			//创建一EasyUITreeNode节点
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCategory.getId());//主键
			node.setText(contentCategory.getName());
			
			node.setState(contentCategory.getIsParent()?"closed":"open");//下面有子节点是closed,没有是open
			
			//添加到列表
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public MallResult insertCategory(Long parentId, String name) {
		//创建一个pojo对象
		ContentCategory contentCategory = new ContentCategory();
		contentCategory.setName(name);
		contentCategory.setParentId(parentId);
		//1(正常),2(删除)
		contentCategory.setStatus(1);
		contentCategory.setIsParent(false);
		
		//'排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数'
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		
		//插入数据
		contentCategoryMapper.insert(contentCategory);
		
		//取返回的主键
		Long id = contentCategory.getId();
		
		
		//判断父节点的is parent属性
		//查询父节点
		ContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}

		return MallResult.ok(id);
	}
	//删除商品分类
	@Override
	public MallResult deleteContentCategory(long id) {
		ContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		//判断删除的节点是否为父类
		if(contentCategory.getIsParent()){
			List<ContentCategory> list=getContentCategoryListByParentId(id);//获取父节点下的所有子节点
			//递归删除
			for(ContentCategory tbContentCategory : list){
				deleteContentCategory(tbContentCategory.getId());
			}
		}
		//判断父类中是否还有子类节点，没有的话，把父类改成子类  
		if(getContentCategoryListByParentId(contentCategory.getParentId()).size()==1)
		{
			ContentCategory parentCategory=contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
			parentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentCategory);
		}
		contentCategoryMapper.deleteByPrimaryKey(id);
		return  MallResult.ok();
	}
	/**
	 * 获取该节点下的孩子节点
	 * @param id
	 * @return 父节点下的所有孩子节点
	 */
	//通过父节点id来查询所有子节点，封装方法
	private List<ContentCategory> getContentCategoryListByParentId(long id){
		ContentCategoryExample example = new ContentCategoryExample();
		ContentCategoryExample.Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<ContentCategory> list = contentCategoryMapper.selectByExample(example);
		return list;
	}

	/**
	 * 更新商品分类名称
	 * @param id
	 * @param name
	 * @return
	 */
	@Override
	public MallResult updateContentCategory(long id, String name) {
		// 查询到要修改的对象
		ContentCategory node = contentCategoryMapper.selectByPrimaryKey(id);
		// 更新名字
		node.setName(name);
		contentCategoryMapper.updateByPrimaryKey(node);
		return MallResult.ok();
	}
}