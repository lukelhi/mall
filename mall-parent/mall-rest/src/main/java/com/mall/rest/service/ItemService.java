package com.mall.rest.service;

import com.mall.pojo.Item;
import com.mall.pojo.ItemDesc;
import com.mall.pojo.ItemParamItem;

public interface ItemService {
	Item getItemById(Long itemId);
	ItemDesc getItemDescById(Long itemId);
	ItemParamItem getItemParamById(Long itemId);
}
