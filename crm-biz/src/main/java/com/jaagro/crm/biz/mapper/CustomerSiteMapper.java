package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerSite;

public interface CustomerSiteMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerSite record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerSite record);

    /**
     * @mbggenerated 2018-08-23
     */
    CustomerSite selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerSite record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerSite record);

}