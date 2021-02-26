package com.mall.rest.service;

import com.mall.pojo.Content;
import com.mall.pojo.MallResult;

import java.util.List;

public interface ContentService {
	List<Content> getContentList(long cid);
	MallResult syncContent(long cid);//同步服务
}
