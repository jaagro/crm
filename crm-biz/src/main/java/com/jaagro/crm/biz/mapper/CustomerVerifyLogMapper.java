package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerVerifyLog;

/**
 * @author baiyiran
 */
public interface CustomerVerifyLogMapper {
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
    int insert(CustomerVerifyLog record);

    /**
     * 动态新增
     *
     * @param record
     * @return
     */
    int insertSelective(CustomerVerifyLog record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    CustomerVerifyLog selectByPrimaryKey(Long id);

    /**
     * 动态更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerVerifyLog record);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(CustomerVerifyLog record);
}