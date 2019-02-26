package com.jaagro.crm.biz.service.impl;

import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.dto.response.tenant.GetTenantDto;
import com.jaagro.crm.api.service.TenantService;
import com.jaagro.crm.biz.entity.Tenant;
import com.jaagro.crm.biz.mapper.TenantMapperExt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tonyZheng
 * @date 2019-02-26 16:59
 */
@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantMapperExt tenantMapperExt;
    @Autowired
    private CurrentUserService currentUserService;

    @Override
    public GetTenantDto getCurrentTenant() {
        UserInfo userInfo = currentUserService.getCurrentUser();
        Tenant tenant = tenantMapperExt.selectByPrimaryKey(userInfo.getTenantId());
        if (null == tenant) {
            throw new NullPointerException("tenant must not be null. error user: " + userInfo);
        }
        GetTenantDto tenantDto = new GetTenantDto();
        BeanUtils.copyProperties(tenant, tenantDto);
        return tenantDto;
    }
}
