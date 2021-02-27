package com.mall.portal.service;

import com.mall.portal.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String keyword, int page, int rows);
}
