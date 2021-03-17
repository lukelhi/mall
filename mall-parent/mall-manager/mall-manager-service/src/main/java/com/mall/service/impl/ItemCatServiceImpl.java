package com.mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mall.mapper.ItemCatMapper;
import com.mall.pojo.EasyUITreeNode;
import com.mall.pojo.ItemCat;
import com.mall.pojo.ItemCatExample;
import com.mall.service.ItemCatService;
import com.mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public List<EasyUITreeNode> getCatItemList(long parentId) {
		//根据parentId查询分类列表
		
		ItemCatExample example = new ItemCatExample();
		//设置查询条件，parentId
		ItemCatExample.Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		//执行查询
		List<ItemCat> list = itemCatMapper.selectByExample(example);
		
		//转换成EasyUITreeNode列表
		List<EasyUITreeNode> resultList = new ArrayList<>();
		//遍历查询的结果集，将结果及装进列表中
		for (ItemCat ItemCat : list) {
			
			EasyUITreeNode node = new EasyUITreeNode();//一个节点对象
			node.setId(ItemCat.getId());
			node.setText(ItemCat.getName());
			node.setState(ItemCat.getIsParent()?"closed":"open");//是父节点就为close,否则就是open;
			
			//添加到列表中
			resultList.add(node);
			
		}
		return resultList;
	}

}
