package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.TruckQualification;

public interface TruckQualificationMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(TruckQualification record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(TruckQualification record);

    /**
     * @mbggenerated 2018-08-23
     */
    TruckQualification selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(TruckQualification record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(TruckQualification record);

}