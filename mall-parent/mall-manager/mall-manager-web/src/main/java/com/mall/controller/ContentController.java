package com.mall.controller;

import com.mall.pojo.Content;
import com.mall.utils.HttpClientUtil;
import com.mall.pojo.MallResult;
import com.mall.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/save")
	@ResponseBody
	public MallResult insertContent(Content content) {
		
	    MallResult result = contentService.insertContent(content);
	    
	    //调用Mall-rest发布的服务，同步缓存
	    HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_URL+content.getCategoryId());
		return result;
	}
}
