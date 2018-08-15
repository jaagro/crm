package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.CreateContractDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author tony
 */
@FeignClient(value = "crm")
public interface ContractClientService {

    /**
     * 创建合同
     * @param dto
     * @return
     */
    @PostMapping("/createContract")
    Map<String, Object> createContract(@RequestBody CreateContractDto dto);
}
