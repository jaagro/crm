package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.Customer;

public interface CustomerMapper {
    /**
     *
     * @mbggenerated 2018-08-15
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int insert(Customer record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int insertSelective(Customer record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    Customer selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int updateByPrimaryKey(Customer record);
}