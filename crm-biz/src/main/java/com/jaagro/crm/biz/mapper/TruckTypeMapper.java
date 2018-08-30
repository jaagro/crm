package com.jaagro.crm.biz.mapper;


import com.jaagro.crm.api.dto.response.truck.TruckTypeReturnDto;
import com.jaagro.crm.biz.entity.TruckType;

import java.util.List;

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

    /**
     * 查询单个合同类型
     *
     * @param id
     * @return
     */
    TruckTypeReturnDto getById(Integer id);

    /**
     * 查询全部车型
     *
     * @return
     */
    List<TruckTypeReturnDto> listAll();
}