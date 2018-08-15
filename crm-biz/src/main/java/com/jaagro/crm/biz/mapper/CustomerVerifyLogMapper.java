package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerVerifyLog;

public interface CustomerVerifyLogMapper {
    /**
     *
     * @mbggenerated 2018-08-15
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int insert(CustomerVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int insertSelective(CustomerVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    CustomerVerifyLog selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int updateByPrimaryKeySelective(CustomerVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int updateByPrimaryKey(CustomerVerifyLog record);
}