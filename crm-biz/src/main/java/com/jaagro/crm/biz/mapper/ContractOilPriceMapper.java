package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.ContractOilPrice;

/**
 * @author yj
 * @since 20181224
 */
public interface ContractOilPriceMapper {
    /**
     *
     * @mbggenerated 2018-12-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insert(ContractOilPrice record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int insertSelective(ContractOilPrice record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    ContractOilPrice selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKeySelective(ContractOilPrice record);

    /**
     *
     * @mbggenerated 2018-12-24
     */
    int updateByPrimaryKey(ContractOilPrice record);
}