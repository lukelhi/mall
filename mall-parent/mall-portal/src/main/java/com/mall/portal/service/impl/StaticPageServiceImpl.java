package com.mall.portal.service.impl;

import com.mall.pojo.Item;
import com.mall.pojo.MallResult;
import com.mall.portal.service.ItemService;
import com.mall.portal.service.StaticPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service
public class StaticPageServiceImpl implements StaticPageService {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${STATIC_PAGE_PATH}")
	private String STATIC_PAGE_PATH;

	/**
	 *
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	@Override
	public MallResult genItemHtml(Long itemId) throws Exception {
		//商品基本信息
		Item item = itemService.getItemById(itemId);
		//商品描述
		String itemDesc = itemService.getItemDescById(itemId);
		//规格参数
		String itemParam = itemService.getItemParamById(itemId);
		//生成静态页面
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("item.ftl");

		//创建一个数据集
		Map root = new HashMap<>();
		//向数据集中添加属性
		root.put("item", item);
		root.put("itemDesc", itemDesc!=null?itemDesc:"暂无商品详情");
		root.put("itemParam", itemParam!=null?itemParam:"暂无商品参数");
		
		//创建一个Writer对象
		Writer out = new FileWriter(new File(STATIC_PAGE_PATH + itemId + ".html"));
		//生成静态文件
		template.process(root, out);
		out.flush();
		out.close();
		
		return MallResult.ok();
	}
}

