package com.mall.portal.service;

import com.mall.pojo.MallResult;

public interface StaticPageService {
	MallResult genItemHtml(Long itemId) throws Exception;
}
