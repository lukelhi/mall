package com.mall.search.dao;

import com.mall.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

public interface SearchDao {
	SearchResult search(SolrQuery query) throws Exception;
}
