package com.jaagro.crm.biz.mapper;


import com.jaagro.crm.biz.entity.TruckVerifyLog;

public interface TruckVerifyLogMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(TruckVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(TruckVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    TruckVerifyLog selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(TruckVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(TruckVerifyLog record);
}