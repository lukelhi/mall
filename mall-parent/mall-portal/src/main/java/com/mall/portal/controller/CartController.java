package com.mall.portal.controller;

import com.mall.pojo.MallResult;
import com.mall.portal.pojo.CartItem;
import com.mall.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	/***
	 * 添加购物车
	 * @param itemId
	 * @param num
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num,
			HttpServletResponse response, HttpServletRequest request) {
		MallResult result = cartService.addCart(num, itemId, request, response);
		return "cartSuccess";
	}
	/***
	 * 取购物车
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request,Model model) {
		List<CartItem> list = cartService.getCartItem(request);
		model.addAttribute("cartList", list);
		return "cart";
	}
	/**
	 * 更新购物车商品数量
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public MallResult updateCartItemNum(@PathVariable Long itemId, @PathVariable Integer num,
										HttpServletResponse response, HttpServletRequest request) {
		MallResult result = cartService.updateCartItem(itemId, num, request, response);
		return result;
	}
	/**
	 * 删除购物车商品
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,
			HttpServletResponse response, HttpServletRequest request) {
		MallResult result = cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}
}
