package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamDto;

import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckTeamService {

    /**
     * 创建车队对象
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeam(CreateTruckTeamDto dto);

    /**
     * 修改车队
     * @param dto
     * @return
     */
    Map<String, Object> updateTruckTeam(CreateTruckTeamDto dto);

    /**
     * 获取单条车辆
     *
     * @param id
     * @return
     */
    Map<String, Object> getTruckTeamById(Integer id);

    /**
     * 获取车队list
     * @return
     */
    Map<String, Object> listTruckTeam();

    /**
     * 删除单条车队
     * @param id
     * @return
     */
    Map<String, Object> deleteTruckTeam(Integer id);
}
