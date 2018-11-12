package com.jaagro.crm.biz.mapper;


import com.jaagro.crm.api.dto.response.truck.ListTruckTypeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gavin
 */
public interface TruckTypeMapperExt extends TruckTypeMapper {
    /**
     * 查询单个车辆类型
     *
     * @param id
     * @return
     */
    ListTruckTypeDto getById(Integer id);

    /**
     * 查询全部车型
     *
     * @return
     */
    List<ListTruckTypeDto> listAll(@Param("productName") String productName);
}