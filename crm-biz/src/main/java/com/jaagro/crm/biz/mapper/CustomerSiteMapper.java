package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerSite;

public interface CustomerSiteMapper {
    /**
     *
     * @mbggenerated 2018-09-27
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-27
     */
    int insert(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-09-27
     */
    int insertSelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-09-27
     */
    CustomerSite selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-27
     */
    int updateByPrimaryKeySelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-09-27
     */
    int updateByPrimaryKey(CustomerSite record);
}