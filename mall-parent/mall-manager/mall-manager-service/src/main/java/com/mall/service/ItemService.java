package com.mall.service;

import com.mall.pojo.EasyUIDataGridResult;
import com.mall.pojo.Item;
import com.mall.pojo.MallResult;

public interface ItemService {

	Item getItemById(long itemId);
	EasyUIDataGridResult getItemList(int page, int rows);
	MallResult createItem(Item item, String desc, String itemParam);//增加商品
	String getItemParamHtml(long itemId);
	MallResult getItemDescById(Long id);
}
