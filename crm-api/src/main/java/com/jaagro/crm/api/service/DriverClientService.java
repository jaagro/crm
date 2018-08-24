package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.driver.CreateDriverDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author tony
 */
@FeignClient("user")
public interface DriverClientService {

    /**
     * 创建司机对象
     * @param driver
     * @return
     */
    @PostMapping("/driver")
    BaseResponse createDriver(@RequestBody CreateDriverDto driver);
}
