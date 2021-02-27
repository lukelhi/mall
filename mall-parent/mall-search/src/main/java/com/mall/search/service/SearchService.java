package com.mall.search.service;

import com.mall.search.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString, int page, int rows) throws Exception;
}
