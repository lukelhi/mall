package com.mall.sso.service.impl;


import com.mall.mapper.UserMapper;
import com.mall.pojo.MallResult;
import com.mall.pojo.User;
import com.mall.pojo.UserExample;
import com.mall.sso.component.JedisClient;
import com.mall.sso.service.LoginService;
import com.mall.utils.CookieUtils;
import com.mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;




@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    /**
     * 用户登录服务
     * @param username 用户名
     * @param password 密码
     * @param request
     * @param response
     * @return
     */
    @Override
    public MallResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		//校验用户名密码是否正确
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> list = userMapper.selectByExample(example);


        if (list == null || list.isEmpty()) {
            return MallResult.build(400, "该用户不存在");
        }
        //用户名不重复，list中只有一个
        User user = list.get(0);
        // 校验密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return MallResult.build(400, "密码错误");
        }

        //登录成功
        //生成token
        String token = UUID.randomUUID().toString();
        user.setPassword(null);
		//把用户信息写入redis
		//key:REDIS_SESSION:{TOKEN}
		//value:user转json
        jedisClient.set(REDIS_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
        jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);//设置session的过期时间

        // Token写入cookie 浏览器关闭就过期
        CookieUtils.setCookie(request, response, "TT_TOKEN", token);
        return MallResult.ok(token);
    }
    /**
     * *通过token查询用户信息
     * @param token token
     * @return 用户信息
     */
    @Override
    public MallResult getUserByToken(String token) {
        String json = jedisClient.get(REDIS_SESSION_KEY + ":" + token);
        if (StringUtils.isBlank(json)) {
            return MallResult.build(400, "用户session已经过期");
        }
        //把json转换成java对象
        User user = JsonUtils.jsonToPojo(json, User.class);
        //更新session的过期时间
        jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);
        return MallResult.ok(user);
    }


    /**
     * *退出登录
     * @param token
     * @return
     */
    @Override
    public MallResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
        Long result = jedisClient.del(REDIS_SESSION_KEY + ":" + token);
        CookieUtils.deleteCookie(request, response, "TT_TOKEN");
        if (result > 0) {
            return MallResult.ok("退出成功");
        } else {
          return MallResult.ok("用户未登录");
        }
    }
}
