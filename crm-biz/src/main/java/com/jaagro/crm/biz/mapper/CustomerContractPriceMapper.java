package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContractPrice;

/**
 * @author baiyiran
 */
public interface CustomerContractPriceMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerContractPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerContractPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    CustomerContractPrice selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerContractPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerContractPrice record);

}