package com.mall.search.service.impl;


import com.mall.pojo.MallResult;
import com.mall.search.mapper.SearchItemMapper;
import com.mall.search.pojo.SearchItem;
import com.mall.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * 商品导入service
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private SearchItemMapper searchItemMapper;
	@Override
	public MallResult importItems() throws Exception {
		// 查询数据库获得商品列表
		List<SearchItem> itemList = searchItemMapper.getItemList();
		//遍历列表
		for (SearchItem item : itemList) {
			//创建文档对象
			SolrInputDocument document = new SolrInputDocument();
			//添加域
			document.addField("id", item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_category_name", item.getCategory_name());
			document.addField("item_desc", item.getItem_desc());
			//写入索引库
			solrServer.add(document);
		}
		//提交
		solrServer.commit();
		return MallResult.ok();
	}
}
