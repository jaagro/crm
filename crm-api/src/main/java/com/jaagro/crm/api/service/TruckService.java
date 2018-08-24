package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.driver.CreateTruckDto;

import java.util.List;
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
    Map<String, Object> getById(Integer id);

    /**
     * 创建车辆
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruck(List<CreateTruckDto> dto, Integer truckTeamId);
}
