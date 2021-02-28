package com.mall.portal.controller;

import com.mall.pojo.User;
import com.mall.portal.pojo.CartItem;
import com.mall.portal.pojo.OrderInfo;
import com.mall.portal.service.CartService;
import com.mall.portal.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	/*
	 * 展示订单
	 * 
	 */
	@RequestMapping("/order-cart")
	public String showOrderCart(Model model, HttpServletRequest request) {
		//取购物车商品列表
		List<CartItem> list = cartService.getCartItem(request);
		model.addAttribute("cartList", list);
		return "order-cart";
	}
	/**
	 * 创建订单
	 * 补全用户信息调用service
	 */
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, Model model, HttpServletRequest request) {
		
		//取用户信息
		User user = (User) request.getAttribute("user");
		//补全orderIn的属性
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		
		//调用服务 
		
		String orderId = orderService.createOrder(orderInfo);
		
		//把订单号传递个页面
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());
		
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);//收货时间向后加三天
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		//返回逻辑视图
		return "success";
	}

}
