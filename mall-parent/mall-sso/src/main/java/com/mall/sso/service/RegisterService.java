package com.mall.sso.service;

import com.mall.pojo.MallResult;
import com.mall.pojo.User;

public interface RegisterService {
    MallResult checkData(String param, int type);

    MallResult register(User user);
}
