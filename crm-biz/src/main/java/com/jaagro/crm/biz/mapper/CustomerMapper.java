package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.Customer;

/**
 * @author baiyiran
 */
public interface CustomerMapper {
    /**
     * 主键删除
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
    int insert(Customer record);

    /**
     * 动态新增
     *
     * @param record
     * @return
     */
    int insertSelective(Customer record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    Customer selectByPrimaryKey(Long id);

    /**
     * 动态更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Customer record);
}