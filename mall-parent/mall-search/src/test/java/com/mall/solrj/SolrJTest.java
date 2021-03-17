package com.mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	/**
	 * 单机版solr
	 * @throws Exception
	 */
	@Test
	public void testSolrJ() throws Exception {
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.133:8080/solr");
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		//添加域
		document.addField("id", "solrtest01");
		document.addField("item_title", "测试商品");
		document.addField("item_sell_point", "卖点");
		//添加到索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}

	@Test
	public void testQuery() throws Exception {
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.133:8080/solr");
		//创建一个查询对象
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_sell_point"));
		}
	}

	/**
	 * 集群版测试
	 * @throws Exception
	 */
	@Test
	public void testSolrCloudAddDocument() throws Exception {

		// 第一步：把solrJ相关的jar包添加到工程中。
		// 第二步：创建一个SolrServer对象，需要使用CloudSolrServer子类。构造方法的参数是zookeeper的地址列表。
		//参数是zookeeper的地址列表，使用逗号分隔
		CloudSolrServer solrServer = new CloudSolrServer("192.168.25.133:2181,192.168.25.133:2182,192.168.25.133:2183");
		// 第三步：需要设置DefaultCollection属性。
		solrServer.setDefaultCollection("collection2");
		// 第四步：创建一SolrInputDocument对象。
		SolrInputDocument document = new SolrInputDocument();
		// 第五步：向文档对象中添加域
		document.addField("item_title", "测试商品");
		document.addField("item_price", "100");
		document.addField("id", "test001");
		// 第六步：把文档对象写入索引库。
		solrServer.add(document);
		// 第七步：提交。
		solrServer.commit();
	}

}
