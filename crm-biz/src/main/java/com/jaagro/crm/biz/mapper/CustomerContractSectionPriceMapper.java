package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContractSectionPrice;

/**
 * @author baiyiran
 */
public interface CustomerContractSectionPriceMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer selectionId);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    CustomerContractSectionPrice selectByPrimaryKey(Integer selectionId);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerContractSectionPrice record);

}