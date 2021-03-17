package com.mall.portal.service.impl;

import com.mall.pojo.Content;
import com.mall.pojo.MallResult;
import com.mall.portal.pojo.AdNode;
import com.mall.portal.service.ContentService;
import com.mall.utils.HttpClientUtil;
import com.mall.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	@Value("${REST_CONTENT_AD_CID}")
	private String REST_CONTENT_AD_CID;
	
	/**
	 * 获得广告位的内容
	 *
	 */
	@Override
	public String getAdList() throws Exception {
		//调用服务获取数据
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_URL+REST_CONTENT_AD_CID);
		//把json转换成java对象
		MallResult mallResult = MallResult.formatToList(json, Content.class);

		if(mallResult == null){
			throw new Exception("没有找到大广告位商品");
		}
		//取data属性,内容列表
		List<Content> contentList = (List<Content>) mallResult.getData();

		//把内容列表转换成AdNode列表
		List<AdNode> resultList = new ArrayList<>();

		for (Content content : contentList) {
			AdNode node = new AdNode();
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(content.getPic());

			node.setHeightB(240);
			node.setWidthB(550);
			node.setSrcB(content.getPic2());

			node.setAlt(content.getSubTitle());
			node.setHref(content.getUrl());

			resultList.add(node);
		}

		//需要把resultList转换成json数据
		String resultJson = JsonUtils.objectToJson(resultList);
		return resultJson;
	}

}
