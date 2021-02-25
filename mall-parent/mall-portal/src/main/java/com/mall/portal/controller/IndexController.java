package com.mall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

//	@Autowired
//	private ContentService contentService;
	
	//web.xml中有index.html欢迎页面,直接被被springmvc拦截，走这个方法
	@RequestMapping("/index")
	public String showIndex(Model model) {
//		//获取大广告位的内容
//		String json = contentService.getAdList();
//		//传递给页面
//		model.addAttribute("ad1",json);
		
		return "index";
	}
}
