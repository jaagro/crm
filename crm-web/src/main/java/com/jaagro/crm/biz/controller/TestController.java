package com.jaagro.crm.biz.controller;

import com.jaagro.crm.api.dto.response.UserDto;
import com.jaagro.crm.api.service.UserClientService;
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
    UserClientService userClientService;

    @GetMapping("/test")
    public UserDto getToken(){
        String token = request.getHeader("token");
        return userClientService.getUserByToken(token);
    }
    @GetMapping("/test2")
    public String test(){
        return "success";
    }
}
