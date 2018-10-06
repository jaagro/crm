package com.jaagro.crm.biz.mapper;


import com.jaagro.crm.biz.entity.TruckType;

public interface TruckTypeMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(TruckType record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(TruckType record);

    /**
     * @mbggenerated 2018-08-23
     */
    TruckType selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(TruckType record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(TruckType record);

}