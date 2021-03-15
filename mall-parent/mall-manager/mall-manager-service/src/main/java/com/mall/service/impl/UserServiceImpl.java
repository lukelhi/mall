package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.mapper.UserMapper;
import com.mall.pojo.*;
import com.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public EasyUIDataGridResult getUserList(Integer page, Integer rows) {
        //分页处理
        PageHelper.startPage(page, rows);
        //执行查询
        UserExample example = new UserExample();
        List<User> list = userMapper.selectByExample(example);
        //取分页的信息
        //User跟jsp中的field对应
        PageInfo<User> pageInfo = new PageInfo<>(list);//将list作为参数获取pageInfo对象

        ///返回处理结果
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        //将定义的pojo返回
        return result;
    }

    //新增用户
    @Override
    public MallResult addUser(User user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //查询用户名是否重复
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() > 0)return MallResult.build(501,"用户名重复");
        //用户名不重复可以添加用户
        int insertSelective = userMapper.insertSelective(user);
        if(insertSelective > 0) return MallResult.ok();
        else return MallResult.build(500,"用户没有成功添加！！");
    }

    @Override
    public MallResult deleteUser(String ids) {
        String[] buff = ids.split(",");
        for (String id : buff) {
            // 删除用户信息
            userMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        return MallResult.ok();
    }
}
