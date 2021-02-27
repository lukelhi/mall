package com.mall.sso.service.impl;

import com.mall.mapper.UserMapper;
import com.mall.pojo.MallResult;
import com.mall.pojo.User;
import com.mall.pojo.UserExample;
import com.mall.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 注册服务
 *
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public MallResult checkData(String param, int type) {
    	//根据数据类型检查数据
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		//1、2、3分别代表userName、phone、email
		if (1 == type) {
			criteria.andUsernameEqualTo(param);
		} else if ( 2 == type) {
			criteria.andPhoneEqualTo(param);
		} else if ( 3 == type ) {
			criteria.andEmailEqualTo(param);
		}
		//执行查询
		List<User> list = userMapper.selectByExample(example);
		//判断查询结果是否为空
		if (list == null || list.isEmpty()) {
			return MallResult.ok(true);
		}
		return MallResult.ok(false);
	}


    @Override
    public MallResult register(User user) {
        // 校验数据
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return MallResult.build(400, "用户名或密码不能为空");
        }
        MallResult result = checkData(user.getUsername(), 1);
        if (!(boolean) result.getData()) {
            return MallResult.build(400, "用户名重复");
        }
        if (user.getPhone() != null) {
            result = checkData(user.getPhone(), 2);
            if (!(boolean) result.getData()) {
                return MallResult.build(400, "手机号重复");
            }
        }
        if (user.getEmail() != null) {
            result = checkData(user.getEmail(), 3);
            if (!(boolean) result.getData()) {
                return MallResult.build(400, "邮箱重复");
            }
        }

        // 插入数据
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //使用MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);

        return MallResult.ok();
    }
}
