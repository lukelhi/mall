package com.mall.service;

import com.mall.pojo.Content;
import com.mall.pojo.EasyUIDataGridResult;
import com.mall.pojo.MallResult;

public interface ContentService {
	MallResult insertContent(Content content);
	EasyUIDataGridResult getContentList(Integer page,Integer rows,Integer categoryId);
	MallResult getContent(String id);
	MallResult updateContent(Content content);
	MallResult deleteContent(String ids);
}
