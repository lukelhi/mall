package com.mall.portal.controller;

import com.mall.portal.pojo.SearchResult;
import com.mall.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * 实现商品查询
 * @author 李红义
 *
 */
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String keyword,
			@RequestParam(defaultValue="1")Integer page,
			@RequestParam(defaultValue="60")Integer rows,Model model) {
		//get乱码处理
		try {

			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");

		} catch (UnsupportedEncodingException e) {
			keyword = "";
			e.printStackTrace();
		}
		
		SearchResult searchResult = searchService.search(keyword, page, rows);
		model.addAttribute("query",keyword);
		model.addAttribute("totalPages",searchResult.getPageCount());
		model.addAttribute("itemList",searchResult.getItemList());
		model.addAttribute("page",searchResult.getCurPage());
		
		//返回逻辑视图
		return "search";
	}
	
}
