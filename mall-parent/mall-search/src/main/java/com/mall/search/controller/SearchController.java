package com.mall.search.controller;

import com.mall.utils.ExceptionUtil;
import com.mall.pojo.MallResult;
import com.mall.search.pojo.SearchResult;
import com.mall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/q")
	@ResponseBody
	public MallResult search(@RequestParam(defaultValue="")String keyword,
							 @RequestParam(defaultValue="1")Integer page,
							 @RequestParam(defaultValue="30")Integer rows) {
		
		try {
			//解决get乱码问题，post有过滤器
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
			
			SearchResult searchResult = searchService.search(keyword, page, rows);
			
			//System.out.println("controller:\n"+searchResult);
			
			return MallResult.ok(searchResult);
		}catch (Exception e) {
			e.printStackTrace();
			return MallResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}
}
