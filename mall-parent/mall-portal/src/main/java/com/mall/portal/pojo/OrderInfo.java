package com.mall.portal.pojo;

import com.mall.pojo.Order;
import com.mall.pojo.OrderItem;
import com.mall.pojo.OrderShipping;

import java.util.List;

//接收参数的pojo
public class OrderInfo extends Order {

	private List<OrderItem> orderItems;
	private OrderShipping orderShipping;

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public OrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}


}
