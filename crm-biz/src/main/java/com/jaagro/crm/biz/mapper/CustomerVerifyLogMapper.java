package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerVerifyLog;

public interface CustomerVerifyLogMapper {
    /**
     *
     * @mbggenerated 2018-08-14
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int insert(CustomerVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int insertSelective(CustomerVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    CustomerVerifyLog selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int updateByPrimaryKeySelective(CustomerVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int updateByPrimaryKey(CustomerVerifyLog record);
}