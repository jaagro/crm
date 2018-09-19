package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContract;
/**
 * @author baiyiran
 */
public interface CustomerContractMapper{
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerContract record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerContract record);

    /**
     * @mbggenerated 2018-08-23
     */
    CustomerContract selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerContract record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerContract record);

}