package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContract;

/**
 * @author baiyiran
 */
public interface CustomerContractMapper {

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
    int insert(CustomerContract record);

    /**
     * 动态新增
     *
     * @param record
     * @return
     */
    int insertSelective(CustomerContract record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    CustomerContract selectByPrimaryKey(Long id);

    /**
     * 动态更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerContract record);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(CustomerContract record);
}