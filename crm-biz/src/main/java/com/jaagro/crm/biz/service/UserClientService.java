package com.jaagro.crm.biz.service;

import com.jaagro.constant.UserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yj
 * @since 2018/11/19
 */
@FeignClient(value = "user")
public interface UserClientService {
    /**
     * 根据id列表查用户列表
     *
     * @param userIdList
     * @param userType
     * @return
     */
    @GetMapping("/listUserInfo")
    List<UserInfo> listUserInfo(@RequestParam("userIdList") List<Integer> userIdList, @RequestParam("userType") String userType);
}
