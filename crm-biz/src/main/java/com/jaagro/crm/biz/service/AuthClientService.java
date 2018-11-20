package com.jaagro.crm.biz.service;

import com.jaagro.constant.UserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 入参必须要有注解
 * @author tony
 */
@FeignClient(value = "auth")
public interface AuthClientService {
    /**
     * 获取token相关的用户信息
     * @param token
     * @return
     */
    @PostMapping("/getUserByToken")
    UserInfo getUserByToken(@RequestParam("token") String token);
}
