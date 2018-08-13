package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.response.TokenInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 入参必须要有注解
 * @author tony
 */
@FeignClient(value = "auth")
public interface TokenInfoClient {
    /**
     * 获取token相关的用户信息
     * 提供给其他微服务使用
     * @param token
     * @return
     */
    @PostMapping("/getTokenInfo")
    TokenInfo getTokenInfo(@RequestParam("token") String token);
}
