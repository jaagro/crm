package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateTruckDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckCriteriaDto;
import com.jaagro.crm.api.dto.response.truck.GetTruckDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTypeDto;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckService {
    /**
     * 获取单条车辆
     * @param truckId
     * @return
     */
    Map<String, Object> getTruckById(Integer truckId);

    /**
     * 获取单条车辆 返回对象
     * @param truckId
     * @return
     */
    GetTruckDto getTruckByIdReturnObject(Integer truckId);

    /**
     * 创建车辆
     *
     * @param truckDto
     * @return
     */
    Map<String, Object> createTruck(CreateTruckDto truckDto);

    /**
     * 修改车辆
     *
     * @param truckDto
     * @return
     */
    Map<String, Object> updateTruck(CreateTruckDto truckDto);

    /**
     * 删除车辆
     *
     * @param id
     * @return
     */
    Map<String, Object> deleteTruck(Integer id);

    /**
     * 获取list
     *
     * @param criteriaDto
     * @return
     */
    Map<String, Object> listTruck(ListTruckCriteriaDto criteriaDto);

    /**
     * 运力管理
     *
     * @param criteriaDto
     * @return
     */
    Map<String, Object> listTruckByCriteria(ListTruckCriteriaDto criteriaDto);

    /**
     * 获取车辆类型列表
     *
     * @return
     */
    List<ListTruckTypeDto> listTruckType();

    /**
     * 获取单条车辆类型
     * @param id
     * @return
     */
    ListTruckTypeDto  getTruckTypeById(Integer id);
}
