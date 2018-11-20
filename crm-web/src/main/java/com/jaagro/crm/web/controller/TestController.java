package com.jaagro.crm.web.controller;

import com.jaagro.constant.UserInfo;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.crm.biz.service.AuthClientService;
import com.jaagro.crm.biz.service.impl.CurrentUserService;
import com.jaagro.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tony
 */
@RestController
public class TestController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    AuthClientService authClientService;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    OssSignUrlClientService ossSignUrlClientService;

    @GetMapping("/test")
    public UserInfo getToken() {
        String token = request.getHeader("token");
        return authClientService.getUserByToken(token);
    }

    @GetMapping("/test2")
    public UserInfo test() {
        return currentUserService.getCurrentUser();
    }

    @GetMapping("/debug")
    public BaseResponse debug() {
        int a = 10;
        try{
//            a = 10 / 0;
        } catch (Exception e){
            throw new NullPointerException("我是提供方抛出的异常： " + e.getMessage());
        }
        return BaseResponse.successInstance(a);
    }
}
