package com.mall.portal.service.impl;

import com.mall.pojo.MallResult;
import com.mall.pojo.User;
import com.mall.portal.service.UserService;
import com.mall.utils.CookieUtils;
import com.mall.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {
	
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN_SERVICE}")
	private String SSO_USER_TOKEN_SERVICE;

	@Override
	public User getUserByToken(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			//取token
			String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
			//判断token是否有值
			if(StringUtils.isBlank(token)) {
				return null;
			}
			
			//System.out.println("token:"+token);
			
			//调用sso的服务查询用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN_SERVICE + token);
			//把json转换成java对象
			MallResult result = MallResult.format(json);
			if (result.getStatus() != 200) {
				return null;
			}
			
			//取用户对象
			result = MallResult.formatToPojo(json, User.class);
			User user = (User) result.getData();
			return user;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
}