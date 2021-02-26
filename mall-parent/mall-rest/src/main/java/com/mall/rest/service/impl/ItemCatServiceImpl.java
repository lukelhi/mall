package com.mall.rest.service.impl;

import com.mall.mapper.ItemCatMapper;
import com.mall.pojo.ItemCat;
import com.mall.pojo.ItemCatExample;
import com.mall.rest.pojo.CatNode;
import com.mall.rest.pojo.ItemCatResult;
import com.mall.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Override
	public ItemCatResult getItemCatList() {
		
		//调用	递归方法	查询商品分类列表
		List catList = getItemCatList(0l);
		//返回结果
		ItemCatResult result = new ItemCatResult();
		result.setData(catList);
		return result;
	}
	
	//递归方法
	private List getItemCatList(long parentId) {
		
		ItemCatExample example = new ItemCatExample();
		ItemCatExample.Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<ItemCat> list = itemCatMapper.selectByExample(example);
		List resultList = new ArrayList();
		int index=0;
		for(ItemCat itemCat : list) {
			//限制第一级节点不超过四个
			if(index>=14) {
				break;
			}
			//根据json格式拼接成json字符串
			
			//如果是父节点
			if(itemCat.getIsParent()) {
				CatNode node = new CatNode();
				node.setUrl("/products/"+itemCat.getId()+".html");
			
				//如果当前节点是第一级节点
				if(itemCat.getParentId() == 0) {
					node.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
					index++;//index是计数器，不能超过十四个
				}else {
					node.setName(itemCat.getName());
				}

				//递归调用查询items
				node.setItems(getItemCatList(itemCat.getId()));
				//把node添加到列表
				resultList.add(node);

			}else {
				//如果是叶子节点
				String item = "/products/"+itemCat.getId()+".html|" + itemCat.getName();
				resultList.add(item);
			}
		}
		return resultList;
		
	}
}