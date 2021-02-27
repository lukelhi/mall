package com.mall.sso.service;

import com.mall.pojo.MallResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    MallResult login(String username, String password, HttpServletRequest request, HttpServletResponse response);

    MallResult getUserByToken(String token);

    MallResult logout(String token, HttpServletRequest request, HttpServletResponse response);
}
