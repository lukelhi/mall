package com.mall.controller;

import com.mall.pojo.Content;
import com.mall.pojo.EasyUIDataGridResult;
import com.mall.pojo.MallResult;
import com.mall.service.ContentService;
import com.mall.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(@RequestParam(defaultValue="1") Integer page,
											   @RequestParam(defaultValue="30") Integer pageSize,Integer categoryId){
		EasyUIDataGridResult result = contentService.getContentList(page,pageSize,categoryId);
		return result;
	}

	/**
	 * 根据id获取商品内容
	 * @param id
	 * @return
	 */
	@RequestMapping("/getContent")
	@ResponseBody
	public MallResult getContent(String id) {
		MallResult result = contentService.getContent(id);
		return result;
	}
	@RequestMapping("/edit")
	@ResponseBody
	public MallResult updateContent(Content content){
		MallResult result = contentService.updateContent(content);
		return result;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public MallResult deleteContent(String ids){
		MallResult result = contentService.deleteContent(ids);
		return result;
	}
}
