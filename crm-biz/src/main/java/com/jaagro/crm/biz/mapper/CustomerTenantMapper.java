package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerTenant;

public interface CustomerTenantMapper {
    /**
     *
     * @mbggenerated 2019-02-21
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-02-21
     */
    int insert(CustomerTenant record);

    /**
     *
     * @mbggenerated 2019-02-21
     */
    int insertSelective(CustomerTenant record);

    /**
     *
     * @mbggenerated 2019-02-21
     */
    CustomerTenant selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-02-21
     */
    int updateByPrimaryKeySelective(CustomerTenant record);

    /**
     *
     * @mbggenerated 2019-02-21
     */
    int updateByPrimaryKey(CustomerTenant record);
}