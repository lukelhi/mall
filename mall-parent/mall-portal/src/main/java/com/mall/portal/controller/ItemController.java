package com.mall.portal.controller;

import com.mall.pojo.Item;
import com.mall.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * 获取商品基本信息
	 *
	 */
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId,Model model) {
		Item item = itemService.getItemById(itemId);
		model.addAttribute("item",item);
		return "item";//image多个图片展示
	}
	
	/**
	 *  获取商品描述
	 *
	 */
	@RequestMapping(value="/item/desc/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")//解决乱码问题
	@ResponseBody
	public String getItemDesc(@PathVariable Long itemId) {
		String desc = itemService.getItemDescById(itemId);
		return desc;
	}
	
	/**
	 * 获取商品参数信息
	 *
	 */
	@RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable Long itemId) {
		String paramHtml = itemService.getItemParamById(itemId);
		return paramHtml;
	}
}
