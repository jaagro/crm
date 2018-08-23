package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.ContractLog;

public interface ContractLogMapper {

    /**
     * @mbggenerated 2018-08-22
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-22
     */
    int insert(ContractLog record);

    /**
     * @mbggenerated 2018-08-22
     */
    int insertSelective(ContractLog record);

    /**
     * @mbggenerated 2018-08-22
     */
    ContractLog selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-22
     */
    int updateByPrimaryKeySelective(ContractLog record);

    /**
     * @mbggenerated 2018-08-22
     */
    int updateByPrimaryKey(ContractLog record);
}