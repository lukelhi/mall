package com.mall.rest.controller;

import com.mall.pojo.Content;
import com.mall.utils.ExceptionUtil;
import com.mall.pojo.MallResult;
import com.mall.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;

	/**
	 * 根据cid查询商品内容
	 *
	 * @param cid
	 * @return
	 */
	@RequestMapping("/content/{cid}")
	@ResponseBody
	public MallResult getContentList(@PathVariable long cid) {
		try {
			List<Content> list = contentService.getContentList(cid);
			return MallResult.ok(list);
		}catch (Exception e) {
			e.printStackTrace();
			return MallResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 缓存同步操作
	 * @param cid
	 * @return
	 */
	@RequestMapping("/sync/content/{cid}")
	@ResponseBody
	public MallResult syncContent(@PathVariable Long cid) {

		try{
			MallResult result =contentService.syncContent(cid);
			return result;//正常返回
		}catch (Exception e) {
			e.printStackTrace();
			return MallResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
