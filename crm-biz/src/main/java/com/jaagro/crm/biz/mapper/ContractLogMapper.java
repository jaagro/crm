package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContractLog;

public interface ContractLogMapper {

    /**
     * @mbggenerated 2018-08-22
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-22
     */
    int insert(CustomerContractLog record);

    /**
     * @mbggenerated 2018-08-22
     */
    int insertSelective(CustomerContractLog record);

    /**
     * @mbggenerated 2018-08-22
     */
    CustomerContractLog selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-22
     */
    int updateByPrimaryKeySelective(CustomerContractLog record);

    /**
     * @mbggenerated 2018-08-22
     */
    int updateByPrimaryKey(CustomerContractLog record);
}