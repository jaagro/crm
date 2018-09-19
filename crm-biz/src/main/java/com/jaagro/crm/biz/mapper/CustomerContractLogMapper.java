package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContractLog;
/**
 * @author baiyiran
 */
public interface CustomerContractLogMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerContractLog record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerContractLog record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    CustomerContractLog selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerContractLog record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerContractLog record);
}