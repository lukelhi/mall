package com.mall.order.controller;

import com.mall.order.pojo.OrderInfo;
import com.mall.order.service.OrderService;
import com.mall.pojo.MallResult;
import com.mall.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	/*
	 * 创建订单
	 */
	@RequestMapping(value="/order/create", method=RequestMethod.POST)
	@ResponseBody
	public MallResult createOrder(@RequestBody OrderInfo orderInfo) {

		try {
			//System.out.println("orderInfo:"+orderInfo.toString());

			MallResult result = orderService.createOrder(orderInfo);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return MallResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
