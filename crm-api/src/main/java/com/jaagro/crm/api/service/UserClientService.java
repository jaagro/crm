package com.jaagro.crm.api.service;

import com.jaagro.constant.UserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 入参必须要有注解
 * @author tony
 */
@FeignClient(value = "auth")
public interface UserClientService {
    /**
     * 获取token相关的用户信息
     * @param token
     * @return
     */
    @PostMapping("/getUserByToken")
    UserInfo getUserByToken(@RequestParam("token") String token);
}
