package com.mall.service;

import com.mall.pojo.EasyUITreeNode;

import java.util.List;


public interface ItemCatService {
	List<EasyUITreeNode> getCatItemList(long parentId);
}
