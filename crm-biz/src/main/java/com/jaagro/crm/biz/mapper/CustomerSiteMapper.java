package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerSite;

/**
 * @author baiyiran
 */
public interface CustomerSiteMapper {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(CustomerSite record);

    /**
     * 动态新增
     *
     * @param record
     * @return
     */
    int insertSelective(CustomerSite record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    CustomerSite selectByPrimaryKey(Long id);

    /**
     * 动态更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerSite record);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(CustomerSite record);
}