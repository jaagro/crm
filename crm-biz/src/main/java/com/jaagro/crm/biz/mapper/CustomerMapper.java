package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.Customer;

public interface CustomerMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(Customer record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(Customer record);

    /**
     * @mbggenerated 2018-08-23
     */
    Customer selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(Customer record);

}