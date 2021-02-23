package com.mall.service;

import com.mall.pojo.Item;

public interface ItemService {

	Item getItemById(long itemId);
	//EasyUIDataGridResult getItemList(int page, int rows);
	//TaotaoResult createItem(Item item, String desc, String itemParam);//增加商品
	//String getItemParamHtml(long itemId);
}
