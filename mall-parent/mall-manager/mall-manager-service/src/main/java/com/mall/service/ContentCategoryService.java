package com.mall.service;

import com.mall.pojo.EasyUITreeNode;
import com.mall.pojo.MallResult;

import java.util.List;

public interface ContentCategoryService {
	List<EasyUITreeNode> getContentCatList(Long parentId);
	MallResult insertCategory(Long parentId, String name);
}
