package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.response.tenant.GetTenantDto;

/**
 * @author tonyZheng
 * @date 2019-02-26 16:56
 */
public interface TenantService {

    /**
     * 获取当前租户详情
     * @param id
     * @return
     */
    GetTenantDto getTenantById(int id);
}
