package com.mall.rest.controller;

import com.mall.rest.pojo.ItemCatResult;
import com.mall.rest.service.ItemCatService;
import com.mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;

	//controller支持jsonp的两种方式

	//使用第二种方法

	@RequestMapping(value="/list1",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")//json形式响应，设置contextType和字符集
	@ResponseBody
	public String getItemCatList1(String callback) {
		ItemCatResult result = itemCatService.getItemCatList();
		if (StringUtils.isBlank(callback)) {//如果callback是空的
			//需要把result转换成字符串
			String json = JsonUtils.objectToJson(result);
			return json;
		}
		//如果字符串不为空，需要支持jsonp调用
		//需要把result转换成字符串
		String json = JsonUtils.objectToJson(result);
		return callback + "(" + json + ");";//拼接字符串
	}

	//第二种方法：不需要做字符串转化
	//需要使用mappingJacksonValue
	@RequestMapping(value="/list")
	@ResponseBody
	public Object getItemCatList(String callback) {
		ItemCatResult result = itemCatService.getItemCatList();
		if (StringUtils.isBlank(callback)) {
			//需要把result转换成字符串
			return result;
		}
		//如果字符串不为空，需要支持jsonp调用
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);//将pojo包装成jsonp
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}

}
