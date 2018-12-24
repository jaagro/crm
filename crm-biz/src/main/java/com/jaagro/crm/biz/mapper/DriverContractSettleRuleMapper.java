package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.DriverContractSettleRule;

public interface DriverContractSettleRuleMapper {
    /**
     *
     * @mbggenerated 2018-12-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insert(DriverContractSettleRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insertSelective(DriverContractSettleRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    DriverContractSettleRule selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKeySelective(DriverContractSettleRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKey(DriverContractSettleRule record);
}