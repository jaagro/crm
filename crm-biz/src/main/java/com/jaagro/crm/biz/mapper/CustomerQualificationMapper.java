package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerQualification;

public interface CustomerQualificationMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerQualification record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerQualification record);

    /**
     * @mbggenerated 2018-08-23
     */
    CustomerQualification selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerQualification record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerQualification record);

}