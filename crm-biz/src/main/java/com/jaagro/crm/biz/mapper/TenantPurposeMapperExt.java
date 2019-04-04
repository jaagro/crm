package com.jaagro.crm.biz.mapper;


import com.jaagro.crm.api.dto.request.league.TenantPuroseCerteria;
import com.jaagro.crm.api.dto.request.league.TenantPurposeDto;


import java.util.List;

public interface TenantPurposeMapperExt extends TenantPurposeMapper {

    List<TenantPurposeDto> listTenantPurpose(TenantPuroseCerteria certeria);


}