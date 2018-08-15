package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerSite;

public interface CustomerSiteMapper {
    /**
     *
     * @mbggenerated 2018-08-14
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int insert(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int insertSelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    CustomerSite selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int updateByPrimaryKeySelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-14
     */
    int updateByPrimaryKey(CustomerSite record);
}