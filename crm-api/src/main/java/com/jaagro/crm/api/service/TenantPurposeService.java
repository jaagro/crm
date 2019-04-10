package com.jaagro.crm.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.league.CreateTenantPurposeDto;
import com.jaagro.crm.api.dto.request.league.PurposeDisposeDto;
import com.jaagro.crm.api.dto.request.league.TenantPuroseCerteria;
import com.jaagro.crm.api.dto.request.league.TenantPurposeDetailsDto;


/**
 * @description: 养殖端体验意向服务
 * @author: @Gao.
 * @create: 2019-04-03 16:48
 **/
public interface TenantPurposeService {

    /**
     * 新增养殖户体验意向
     *
     * @param dto
     */
    void insertTenantPurpose(CreateTenantPurposeDto dto);

    /**
     * 养殖意向管理
     *
     * @param certeria
     * @return
     */
    PageInfo listTenantPurpose(TenantPuroseCerteria certeria);

    /**
     * 养殖端详情
     *
     * @param tenantPurposeId
     * @return
     */
    TenantPurposeDetailsDto tenantPurposeDetails(Integer tenantPurposeId);

    /**
     * 养殖意向处理
     *
     * @param purposeDisposeDto
     */
    void tenantPurposeDispose(PurposeDisposeDto purposeDisposeDto);
}
