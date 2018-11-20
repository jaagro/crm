package com.jaagro.crm.biz.service.impl;

import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.dto.base.GetCustomerUserDto;
import com.jaagro.crm.biz.service.DriverClientService;
import com.jaagro.crm.biz.service.AuthClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于获取当先token对应的user对象，封装在api层是为了让每个微服务可以直接调用
 *
 * @author tony
 */
@Service
public class CurrentUserService {
    @Autowired
    private AuthClientService tokenClient;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DriverClientService userClientService;

    public UserInfo getCurrentUser() {
        String token = request.getHeader("token");
        return tokenClient.getUserByToken(token);
    }

    public GetCustomerUserDto getCustomerUserById() {
        return userClientService.getCustomerUserById(getCurrentUser().getId());
    }
}
