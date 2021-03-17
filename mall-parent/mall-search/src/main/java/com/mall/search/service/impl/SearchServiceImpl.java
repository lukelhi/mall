package com.mall.search.service.impl;

import com.mall.search.dao.SearchDao;
import com.mall.search.pojo.SearchResult;
import com.mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		
		//System.out.println("查询条件："+queryString);
		
		// 创建查询条件
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(queryString);
		//设置分页条件
		query.setStart((page-1)*rows);
		//设置默认搜索域
		query.set("df", "item_title");
		
		//设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font class=\"skcolor_ljg\">");//前缀
		query.setHighlightSimplePost("</font>");//后缀
		
		//执行查询
		SearchResult searchResult = searchDao.search(query);
		//计算总页数
		Long recordCount = searchResult.getRecordCount();
		int pageCount = (int) (recordCount / rows);
		if (recordCount % rows > 0) {//多出来的也算是一页
			pageCount++;
		}
		searchResult.setPageCount(pageCount);
		searchResult.setCurPage(page);
		//System.out.println("查询条件："+queryString);
		//System.out.println("service:\n"+searchResult);
		return searchResult;
	}
}
