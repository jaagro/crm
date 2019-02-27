package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.Tenant;

public interface TenantMapper {
    /**
     *
     * @mbggenerated 2019-02-26
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-02-26
     */
    int insert(Tenant record);

    /**
     *
     * @mbggenerated 2019-02-26
     */
    int insertSelective(Tenant record);

    /**
     *
     * @mbggenerated 2019-02-26
     */
    Tenant selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-02-26
     */
    int updateByPrimaryKeySelective(Tenant record);

    /**
     *
     * @mbggenerated 2019-02-26
     */
    int updateByPrimaryKey(Tenant record);
}