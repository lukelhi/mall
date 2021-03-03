package com.mall.controller;

import com.mall.pojo.EasyUIDataGridResult;
import com.mall.pojo.Item;
import com.mall.pojo.MallResult;
import com.mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 根据商品id获取item
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public Item getItemById(@PathVariable Long itemId) {
		Item item = itemService.getItemById(itemId);
		return item;
	}
	/**
	 * 查询商品列表
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(@RequestParam(defaultValue="1") Integer page,
											@RequestParam(defaultValue="30") Integer rows) {
		//调用service查询商品列表
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		//返回结果
		return result;
	}
	/**
	 * 创建商品
	 */
	@RequestMapping(value="/item/save",method= RequestMethod.POST)
	@ResponseBody
	public MallResult addItem(Item item, String desc, String itemParams) {
		//System.out.println("controller:"+item.getCid());
		MallResult result = itemService.createItem(item,desc,itemParams);
		return result;
	}

	/**
	 * 根据商品id获取商品参数
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping("/page/item/{itemId}")
	public String showItemParam(@PathVariable long itemId, Model model) {
		String html = itemService.getItemParamHtml(itemId);
		model.addAttribute("myHtml",html);
		//System.out.println(html);
		return "item-param";
	}
	/**
	 *
	 *	根据商品id获取商品详情
	 *
	 */
	@RequestMapping("/item/desc/{id}")
	@ResponseBody
	public MallResult getItemDesc(@PathVariable Long id){
		MallResult result = itemService.getItemDescById(id);
		return result;
	}
	
}
