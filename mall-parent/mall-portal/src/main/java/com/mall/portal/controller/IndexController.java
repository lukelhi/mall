package com.mall.portal.controller;

import com.mall.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;
	
	//web.xml中有index.html欢迎页面,直接被被springmvc拦截，走这个方法
	@RequestMapping("/index")
	public String showIndex(Model model) throws Exception {
		//获取大广告位的内容
		String json = contentService.getAdList();
		//传递给页面
		model.addAttribute("ad1",json);
		
		return "index";
	}

	/**
	 * 测试httpClient
	 *
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/postTest", method = RequestMethod.POST)
	@ResponseBody
	public String postTest(@RequestBody Map map){ //json数据不加注解,不能识别.使用map,自动将表单转换成pojo。
		System.out.println("name:"+map.get("name"));
		System.out.println("password:"+map.get("password"));
		return "ok";
	}
}
