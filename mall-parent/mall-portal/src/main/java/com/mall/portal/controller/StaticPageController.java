package com.mall.portal.controller;

import com.mall.pojo.MallResult;
import com.mall.portal.service.StaticPageService;
import com.mall.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StaticPageController {

	@Autowired
	private StaticPageService staticPageService;

	/**
	 * 根据商品id生成静态页面
	 *
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/gen/item/{itemId}")
	@ResponseBody
	public MallResult genItemPage(@PathVariable Long itemId) {
		try {
			MallResult result = staticPageService.genItemHtml(itemId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return MallResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}