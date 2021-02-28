package com.mall.order.service;

import com.mall.order.pojo.OrderInfo;
import com.mall.pojo.MallResult;

public interface OrderService {
	MallResult createOrder(OrderInfo orderInfo);
}
