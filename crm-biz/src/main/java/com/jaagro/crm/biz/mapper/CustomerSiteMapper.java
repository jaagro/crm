package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerSite;

public interface CustomerSiteMapper {
    /**
     *
     * @mbggenerated 2018-08-16
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int insert(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int insertSelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    CustomerSite selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKeySelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKey(CustomerSite record);
}