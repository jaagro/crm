package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContractSettleRule;

public interface CustomerContractSettleRuleMapper {
    /**
     *
     * @mbggenerated 2018-12-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insert(CustomerContractSettleRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insertSelective(CustomerContractSettleRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    CustomerContractSettleRule selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKeySelective(CustomerContractSettleRule record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKey(CustomerContractSettleRule record);
}