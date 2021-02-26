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
}