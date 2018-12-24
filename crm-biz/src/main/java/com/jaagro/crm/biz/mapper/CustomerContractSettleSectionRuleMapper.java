package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContractSettleSectionRule;

public interface CustomerContractSettleSectionRuleMapper {
    /**
     *
     * @mbggenerated 2018-12-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insert(CustomerContractSettleSectionRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insertSelective(CustomerContractSettleSectionRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    CustomerContractSettleSectionRule selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKeySelective(CustomerContractSettleSectionRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKey(CustomerContractSettleSectionRule record);
}