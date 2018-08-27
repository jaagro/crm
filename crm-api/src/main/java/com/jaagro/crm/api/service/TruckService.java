package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.driver.CreateTruckDto;
import com.jaagro.crm.api.dto.request.driver.ListTruckCriteriaDto;

import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckService {
    /**
     * 获取单条车辆
     *
     * @param id
     * @return
     */
    Map<String, Object> getTruckById(Integer id);

    /**
     * 创建车辆
     * @param truckDto
     * @return
     */
    Map<String, Object> createTruck(CreateTruckDto truckDto);

    /**
     * 获取list
     * @param criteriaDto
     * @return
     */
    Map<String, Object> listTruck(ListTruckCriteriaDto criteriaDto);
}
