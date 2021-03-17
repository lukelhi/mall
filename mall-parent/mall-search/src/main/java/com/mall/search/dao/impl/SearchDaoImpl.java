package com.mall.search.dao.impl;

import com.mall.search.dao.SearchDao;
import com.mall.search.pojo.SearchItem;
import com.mall.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDaoImpl implements SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		
		//System.out.println("solrQuery:"+query);
		
		// 执行查询
		QueryResponse response = solrServer.query(query);
		
		//取查询结果列表
		SolrDocumentList solrDocumentList = response.getResults();
		
		//System.out.println("dao结果列表："+solrDocumentList);
		
		List<SearchItem> itemList = new ArrayList<>();
		
		for (SolrDocument solrDocument : solrDocumentList) {
			//创建一个SearchItem对象
			SearchItem item = new SearchItem();
			
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setId((String) solrDocument.get("id"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((Long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			item.setTitle((String) solrDocument.get("title"));
			
			
			//将标题高亮显示,（把关键词取出来加上标记）
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			
			//取高亮的标题的list
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			
			String itemTitle = "";
			if (list != null && list.size() > 0) {//如果有高亮就取出来
				//取高亮后的结果
				itemTitle = list.get(0);
			}
			else{//没有高亮取正常的值
				itemTitle = (String) solrDocument.get("item_title");
			}
			
			item.setTitle(itemTitle);
			
			//添加到列表
			itemList.add(item);	
		}
		
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		//查询结果总数量
		result.setRecordCount(solrDocumentList.getNumFound());
		
		//System.out.println("dao："+result);
		return result;
	}
	
}
