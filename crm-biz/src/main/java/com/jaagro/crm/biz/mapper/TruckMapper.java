package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.driver.TruckReturnDto;
import com.jaagro.crm.biz.entity.Truck;

public interface TruckMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(Truck record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(Truck record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    Truck selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(Truck record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(Truck record);

    /**
     * 查询合同dto
     */
    TruckReturnDto getById(Integer id);

}