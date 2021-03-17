package com.mall.search.controller;

import com.mall.utils.ExceptionUtil;
import com.mall.pojo.MallResult;
import com.mall.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	/**
	 * 导入搜索数据索引
	 * @return
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public MallResult importAll() {
		try {
			MallResult result = itemService.importItems();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return MallResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}
}
