package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.ContractQualification;

/**
 * @author baiyiran
 */
public interface ContractQualificationMapper {
    /**
     * @mbggenerated 2018-08-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-24
     */
    int insert(ContractQualification record);

    /**
     * @mbggenerated 2018-08-24
     */
    int insertSelective(ContractQualification record);

    /**
     * @mbggenerated 2018-08-24
     */
    ContractQualification selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-24
     */
    int updateByPrimaryKeySelective(ContractQualification record);

    /**
     * @mbggenerated 2018-08-24
     */
    int updateByPrimaryKey(ContractQualification record);

}