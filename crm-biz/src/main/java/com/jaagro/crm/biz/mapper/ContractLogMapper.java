package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.ContractLog;

public interface ContractLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ContractLog record);

    int insertSelective(ContractLog record);

    ContractLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractLog record);

    int updateByPrimaryKey(ContractLog record);
}