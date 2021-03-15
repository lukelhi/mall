package com.mall.controller;

import com.mall.pojo.EasyUIDataGridResult;
import com.mall.pojo.MallResult;
import com.mall.pojo.User;
import com.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    @ResponseBody
    public EasyUIDataGridResult getUserList(@RequestParam(defaultValue="1") Integer page,
                                            @RequestParam(defaultValue="30") Integer rows) {
        //调用service查询商品列表
        EasyUIDataGridResult result = userService.getUserList(page, rows);
        //返回结果
        return result;
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("/user/add")
    @ResponseBody
    public MallResult AddUser(User user) {
//        System.out.println(user.toString());
        MallResult result = userService.addUser(user);
        //返回结果
        return result;
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @RequestMapping("/user/delete")
    @ResponseBody
    public MallResult DeleteUser(String ids) {

        MallResult result = userService.deleteUser(ids);
        //返回结果
        return result;
    }
}
