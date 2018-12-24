package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.DriverContractSettleSectionRule;

public interface DriverContractSettleSectionRuleMapper {
    /**
     *
     * @mbggenerated 2018-12-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insert(DriverContractSettleSectionRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insertSelective(DriverContractSettleSectionRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    DriverContractSettleSectionRule selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKeySelective(DriverContractSettleSectionRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKey(DriverContractSettleSectionRule record);
}