package com.mall.controller;

import com.mall.pojo.EasyUIDataGridResult;
import com.mall.pojo.MallResult;
import com.mall.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {//新增规格参数例子

	@Autowired
	private ItemParamService itemParamService;

	/**
	 * 查询商品分类的参数模板
	 * @param cid
	 * @return
	 */
	@RequestMapping("/query/itemCatId/{cid}")
	@ResponseBody
	public MallResult getItemCatByCid(@PathVariable long cid) {
		MallResult result = itemParamService.getItemByCid(cid);
		return result;
	}

	/**
	 * 插入规格参数模板
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
	/**
	 * 商品参数模板展示
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult getItemParam(@RequestParam(defaultValue="1") Integer page,
								   @RequestParam(defaultValue="30") Integer pageSize) {
		EasyUIDataGridResult result = itemParamService.getItemParamList(page,pageSize);
		//System.out.println("json数据是："+ result.getTotal());
		return result;
	}
	/**
	 * 根据商品id获取规格参数
	 * 做回显使用
	 *
	 */
	@RequestMapping("/item/query/{id}")
	@ResponseBody
	public MallResult getItemParamsById(@PathVariable Long id){
		MallResult result = itemParamService.getItemByCid(id);
		return result;
	}
	/**
	 * 规格参数模板删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public MallResult deleteItemParamById(String ids){
		MallResult result = itemParamService.deleteItemParamById(ids);
		return result;
	}
}