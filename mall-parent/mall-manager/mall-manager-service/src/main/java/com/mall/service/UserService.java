package com.mall.service;

import com.mall.pojo.EasyUIDataGridResult;
import com.mall.pojo.MallResult;
import com.mall.pojo.User;

public interface UserService {
    EasyUIDataGridResult getUserList(Integer page, Integer rows);
    MallResult addUser(User user);
    MallResult deleteUser(String ids);
}
