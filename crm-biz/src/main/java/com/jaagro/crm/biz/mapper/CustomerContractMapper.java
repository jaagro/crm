package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContract;

public interface CustomerContractMapper {
    /**
     *
     * @mbggenerated 2018-08-14
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int insert(CustomerContract record);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int insertSelective(CustomerContract record);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    CustomerContract selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int updateByPrimaryKeySelective(CustomerContract record);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int updateByPrimaryKey(CustomerContract record);
}