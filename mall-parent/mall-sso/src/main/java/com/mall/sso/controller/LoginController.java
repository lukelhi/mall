package com.mall.sso.controller;

import com.mall.pojo.MallResult;
import com.mall.sso.service.LoginService;
import com.mall.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录
 * @author 李红义
 *
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public MallResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            MallResult result = loginService.login(username, password, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return MallResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 	取token
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        try {
            MallResult result = loginService.getUserByToken(token);
            //支持jsonP
            if (StringUtils.isNotBlank(callback)) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return MallResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 退出登录
     * @param token
     * @param callback
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token, String callback, HttpServletRequest request, HttpServletResponse response) {
        try {
            MallResult result = loginService.logout(token, request, response);
            if (StringUtils.isNotBlank(callback)) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return MallResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
