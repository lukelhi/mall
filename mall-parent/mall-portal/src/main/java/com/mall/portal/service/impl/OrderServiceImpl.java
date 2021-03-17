package com.mall.portal.service.impl;

import com.mall.pojo.MallResult;
import com.mall.portal.pojo.OrderInfo;
import com.mall.portal.service.OrderService;
import com.mall.utils.HttpClientUtil;
import com.mall.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	@Override
	public String createOrder(OrderInfo orderInfo) {
		//把OrderInfo转换成json
		String json = JsonUtils.objectToJson(orderInfo);
		//提交订单数据 doPostJson(url,json)方法
		String jsonResult = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, json);
		//转换成java对象
		MallResult Result = MallResult.format(jsonResult);
		//取订单号
		String orderId = Result.getData().toString();
		return orderId;
	}

}

