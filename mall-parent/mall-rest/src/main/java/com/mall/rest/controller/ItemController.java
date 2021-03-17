package com.mall.rest.controller;

import com.mall.pojo.ItemParamItem;
import com.mall.pojo.MallResult;
import com.mall.rest.service.ItemService;
import com.mall.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理controller
 */
@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 获取商品基本信息
	 *
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/base/{itemId}")
	@ResponseBody
	public MallResult getItemById(@PathVariable Long itemId) {
	    try {
	        return MallResult.ok(itemService.getItemById(itemId));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return MallResult.build(500, ExceptionUtil.getStackTrace(e));
	    }
	}
	
    /**
     * 获取商品详情
     * @param itemId
     * @return
     */
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public MallResult getItemDescById(@PathVariable Long itemId) {
        try {
            return MallResult.ok(itemService.getItemDescById(itemId));
        } catch (Exception e) {
            e.printStackTrace();
            return MallResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    
    /*
     * 获取商品参数
     *
     */
    @RequestMapping("/param/{itemId}")
	@ResponseBody
	public MallResult getItemParamById(@PathVariable Long itemId) {
		try {
			ItemParamItem itemParamItem = itemService.getItemParamById(itemId);
			return MallResult.ok(itemParamItem);
		} catch (Exception e) {
			e.printStackTrace();
			return MallResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
