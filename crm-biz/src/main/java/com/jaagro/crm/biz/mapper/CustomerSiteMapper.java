package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerSite;

public interface CustomerSiteMapper {
    /**
     *
     * @mbggenerated 2018-08-15
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int insert(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int insertSelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    CustomerSite selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int updateByPrimaryKeySelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int updateByPrimaryKey(CustomerSite record);
}