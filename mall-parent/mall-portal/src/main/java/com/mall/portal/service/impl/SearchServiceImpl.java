package com.mall.portal.service.impl;

import com.mall.utils.HttpClientUtil;
import com.mall.pojo.MallResult;
import com.mall.portal.pojo.SearchResult;
import com.mall.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/*
 * 搜索服务实现
 */
@Service
public class SearchServiceImpl implements SearchService {
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String keyword, int page, int rows) {
		// 搜索服务查询商品列表
		Map<String,String> param = new HashMap<String,String>();
		param.put("keyword",keyword);
		param.put("page",page+"");
		param.put("rows",rows+"");
		
		//调用服务
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL,param);
		// 转换成java对象
		MallResult mallResult = MallResult.formatToPojo(json, SearchResult.class);
		
		SearchResult searchResult = (SearchResult)mallResult.getData();
		
		return searchResult;
	}

}
