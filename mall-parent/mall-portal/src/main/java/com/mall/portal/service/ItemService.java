package com.mall.portal.service;

import com.mall.pojo.Item;

public interface ItemService {
	Item getItemById(Long itemId);
	String getItemDescById(Long itemId);
	String getItemParamById(Long itemId);
}
