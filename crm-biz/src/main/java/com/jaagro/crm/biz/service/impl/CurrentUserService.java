package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.response.UserDto;
import com.jaagro.crm.api.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于获取当先token对应的user对象，封装在api层是为了让每个微服务可以直接调用
 * @author tony
 */
@Service
public class CurrentUserService {
    @Autowired
    private UserClientService tokenClient;
    @Autowired
    private HttpServletRequest request;

    public UserDto getCurrentUser(){
        String token = request.getHeader("token");
        return tokenClient.getUserByToken(token);
    }
}
