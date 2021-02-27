package com.mall.sso.controller;

import com.mall.pojo.MallResult;
import com.mall.pojo.User;
import com.mall.sso.service.RegisterService;
import com.mall.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户注册
 */
@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 数据校验接口
     */
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
        try {
            MallResult result = registerService.checkData(param, type);
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

    /*
     * 	注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public MallResult register(User user) {
        try {
            MallResult result = registerService.register(user);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return MallResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
