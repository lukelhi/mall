package com.mall.controller;

import com.mall.pojo.MallResult;
import com.mall.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {//新增规格参数例子

	@Autowired
	private ItemParamService itemParamService;

	/**
	 * 查询规格参数模板
	 * @param cid
	 * @return
	 */
	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public MallResult getItemCatByCid(@PathVariable long cid) {
		MallResult result = itemParamService.getItemByCid(cid);
		return result;
	}

	/**
	 * 插入规格参数
	 * @param cid
	 * @param paramData
	 * @return
	 */
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public MallResult insertItemParam(@PathVariable long cid,String paramData) {
	MallResult result = itemParamService.insertItemParam(cid,paramData);
	//System.out.println("json数据是："+paramData);
	return result;
	}
	
}