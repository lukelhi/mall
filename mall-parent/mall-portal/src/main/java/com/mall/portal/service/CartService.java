package com.mall.portal.service;

import com.mall.pojo.MallResult;
import com.mall.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface CartService {

	MallResult addCart(Integer num, Long itemId, HttpServletRequest request, HttpServletResponse response);
	List<CartItem> getCartItem(HttpServletRequest request);
	MallResult updateCartItem(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);
	MallResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);
}
