package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.TenantPurpose;

public interface TenantPurposeMapper {
    /**
     *
     * @mbggenerated 2019-04-03
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-03
     */
    int insert(TenantPurpose record);

    /**
     *
     * @mbggenerated 2019-04-03
     */
    int insertSelective(TenantPurpose record);

    /**
     *
     * @mbggenerated 2019-04-03
     */
    TenantPurpose selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-03
     */
    int updateByPrimaryKeySelective(TenantPurpose record);

    /**
     *
     * @mbggenerated 2019-04-03
     */
    int updateByPrimaryKey(TenantPurpose record);
}