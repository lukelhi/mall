package com.mall.portal.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mall.pojo.MallResult;
import com.mall.pojo.User;
import com.mall.portal.pojo.CartItem;
import com.mall.portal.pojo.OrderInfo;
import com.mall.portal.service.CartService;
import com.mall.portal.service.OrderService;
import com.mall.utils.AlipayConfig;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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


	//支付宝支付
	@RequestMapping(value="/pay", method=RequestMethod.GET)
	@ResponseBody
	public MallResult payOrder(String orderId, String payment, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = orderId;
		//付款金额，必填
		String total_amount = payment;
		//订单名称，必填
		String subject = orderId;
		//商品描述，可空
		String body = "Mall";

		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
				+ "\"total_amount\":\""+ total_amount +"\","
				+ "\"subject\":\""+ subject +"\","
				+ "\"body\":\""+ body +"\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
		//		+ "\"total_amount\":\""+ total_amount +"\","
		//		+ "\"subject\":\""+ subject +"\","
		//		+ "\"body\":\""+ body +"\","
		//		+ "\"timeout_express\":\"10m\","
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

		//请求
		String result = alipayClient.pageExecute(alipayRequest).getBody();

		//输出
		response.setContentType("text/html");
		response.getWriter().println(result);
		return null;
	}
}
