package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContractSettlePrice;

public interface CustomerContractSettlePriceMapper {
    /**
     *
     * @mbggenerated 2018-12-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insert(CustomerContractSettlePrice record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insertSelective(CustomerContractSettlePrice record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    CustomerContractSettlePrice selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKeySelective(CustomerContractSettlePrice record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKey(CustomerContractSettlePrice record);
}