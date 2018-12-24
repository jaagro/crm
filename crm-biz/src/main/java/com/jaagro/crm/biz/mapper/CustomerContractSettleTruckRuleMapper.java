package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContractSettleTruckRule;

public interface CustomerContractSettleTruckRuleMapper {
    /**
     *
     * @mbggenerated 2018-12-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insert(CustomerContractSettleTruckRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insertSelective(CustomerContractSettleTruckRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    CustomerContractSettleTruckRule selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKeySelective(CustomerContractSettleTruckRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKey(CustomerContractSettleTruckRule record);
}