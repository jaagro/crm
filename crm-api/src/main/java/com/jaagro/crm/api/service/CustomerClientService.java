package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.CreateCustomerDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author liqiangping
 */
@FeignClient(value = "crm")
public interface CustomerClientService {

    /**
     * 创建客户
     * @param dto
     * @return
     */
    @PostMapping("/customer")
    Map<String, Object> createContract(@RequestBody CreateCustomerDto dto);
}
