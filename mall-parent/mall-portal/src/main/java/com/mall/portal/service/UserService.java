package com.mall.portal.service;

import com.mall.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
	User getUserByToken(HttpServletRequest request, HttpServletResponse response);
}
