package com.mall.controller;

import com.mall.pojo.Item;
import com.mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public Item getItemById(@PathVariable Long itemId) {
		Item item = itemService.getItemById(itemId);
		return item;
	}
//	/**
//	 * 查询商品列表
//	 * <p>Title: getItemList</p>
//	 * <p>Description: </p>
//	 * @param page
//	 * @param rows
//	 * @return
//	 */
//	@RequestMapping("/item/list")
//	@ResponseBody
//	public EasyUIDataGridResult getItemList(@RequestParam(defaultValue="1") Integer page,
//			@RequestParam(defaultValue="30") Integer rows) {
//		//调用service查询商品列表
//		EasyUIDataGridResult result = itemService.getItemList(page, rows);
//		//返回结果
//		return result;
//
//	}
//
//	@RequestMapping(value="/item/save",method=RequestMethod.POST)
//	@ResponseBody
//	public TaotaoResult addItem(TbItem item, String desc, String itemParams) {
//		System.out.println("controller:"+item.getCid());
//		TaotaoResult result = itemService.createItem(item,desc,itemParams);
//		return result;
//	}
//
//	@RequestMapping("/page/item/{itemId}")
//	public String showItemParam(@PathVariable long itemId,Model model) {
//		String html = itemService.getItemParamHtml(itemId);
//		model.addAttribute("myhtml",html);
//		//System.out.println(html);
//		return "itemparam";
//	}
	
}
