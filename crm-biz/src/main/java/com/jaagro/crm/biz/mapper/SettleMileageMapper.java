package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.SettleMileage;

public interface SettleMileageMapper {
    /**
     *
     * @mbggenerated 2019-01-17
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-01-17
     */
    int insert(SettleMileage record);

    /**
     *
     * @mbggenerated 2019-01-17
     */
    int insertSelective(SettleMileage record);

    /**
     *
     * @mbggenerated 2019-01-17
     */
    SettleMileage selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-01-17
     */
    int updateByPrimaryKeySelective(SettleMileage record);

    /**
     *
     * @mbggenerated 2019-01-17
     */
    int updateByPrimaryKey(SettleMileage record);
}