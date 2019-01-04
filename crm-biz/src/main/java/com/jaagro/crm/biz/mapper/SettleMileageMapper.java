package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.SettleMileage;

public interface SettleMileageMapper {
    /**
     *
     * @mbggenerated 2018-12-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insert(SettleMileage record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insertSelective(SettleMileage record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    SettleMileage selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKeySelective(SettleMileage record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKey(SettleMileage record);
}