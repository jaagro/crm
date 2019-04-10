package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.league.*;
import com.jaagro.crm.api.enums.ScaleEnum;
import com.jaagro.crm.api.enums.TenantPurposeStatusEnum;
import com.jaagro.crm.api.service.TenantPurposeService;
import com.jaagro.crm.biz.entity.TenantPurpose;
import com.jaagro.crm.biz.mapper.TenantPurposeMapperExt;
import com.jaagro.crm.biz.service.VerificationCodeClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 养殖端体验意向服务
 * @author: @Gao.
 * @create: 2019-04-03 16:50
 **/
@Service
@Slf4j
public class TenantPurposeServiceImpl implements TenantPurposeService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private TenantPurposeMapperExt tenantPurposeMapper;
    @Autowired
    private VerificationCodeClientService verificationCodeClientService;

    /**
     * 新增养殖户体验意向
     *
     * @param dto
     */
    @Override
    public void insertTenantPurpose(CreateTenantPurposeDto dto) {
        boolean existMessage = verificationCodeClientService.existMessage(dto.getPhoneNumber(), dto.getVerificationCode());
        if (!existMessage) {
            throw new RuntimeException("验证码输入有误");
        }
        TenantPurpose tenantPurpose = new TenantPurpose();
        tenantPurpose
                .setStatus(TenantPurposeStatusEnum.UN_DONE.getCode());
        BeanUtils.copyProperties(dto, tenantPurpose);
        tenantPurposeMapper.insertSelective(tenantPurpose);
    }

    /**
     * 养殖意向管理
     *
     * @param certeria
     * @return
     */
    @Override
    public PageInfo listTenantPurpose(TenantPuroseCerteria certeria) {
        PageHelper.startPage(certeria.getPageNum(), certeria.getPageSize());
        List<TenantPurposeDto> tenantPurposeDtos = tenantPurposeMapper.listTenantPurpose(certeria);
        for (TenantPurposeDto tenantPurposeDto : tenantPurposeDtos) {
            tenantPurposeDto.setStrStatus(TenantPurposeStatusEnum.getDescByCode(tenantPurposeDto.getStatus()));
        }
        return new PageInfo(tenantPurposeDtos);
    }

    /**
     * 养殖端详情
     *
     * @param tenantPurposeId
     * @return
     */
    @Override
    public TenantPurposeDetailsDto tenantPurposeDetails(Integer tenantPurposeId) {
        TenantPurpose tenantPurpose = tenantPurposeMapper.selectByPrimaryKey(tenantPurposeId);
        TenantPurposeDetailsDto tenantPurposeDetailsDto = new TenantPurposeDetailsDto();
        if (tenantPurpose != null) {
            BeanUtils.copyProperties(tenantPurpose, tenantPurposeDetailsDto);
            tenantPurposeDetailsDto
                    .setStrStatus(TenantPurposeStatusEnum.getDescByCode(tenantPurpose.getStatus()))
                    .setStrScale(ScaleEnum.getDescByCode(tenantPurpose.getScale()));
        }
        return tenantPurposeDetailsDto;
    }

    /**
     * 养殖意向处理
     *
     * @param purposeDisposeDto
     */
    @Override
    public void tenantPurposeDispose(PurposeDisposeDto purposeDisposeDto) {
        TenantPurpose tenantPurpose = new TenantPurpose();
        tenantPurpose
                .setStatus(TenantPurposeStatusEnum.DONE.getCode())
                .setId(purposeDisposeDto.getTenantPurposeId())
                .setNotes(purposeDisposeDto.getNotes());
        tenantPurposeMapper.updateByPrimaryKeySelective(tenantPurpose);
    }
}
