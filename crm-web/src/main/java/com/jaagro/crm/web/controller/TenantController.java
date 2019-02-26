package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.response.tenant.GetTenantDto;
import com.jaagro.crm.api.service.TenantService;
import com.jaagro.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tonyZheng
 * @date 2019-02-26 17:03
 */
@RestController
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @GetMapping("/getCurrentTenant")
    public BaseResponse<GetTenantDto> getCurrentTenant(){
        GetTenantDto tenant = tenantService.getCurrentTenant();
        return BaseResponse.successInstance(tenant);
    }
}
