package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.driver.CreateTruckTeamDto;

import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckTeamService {

    /**
     * 创建车队
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeam(CreateTruckTeamDto dto);

    /**
     * 创建关联车队
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeams(CreateTruckTeamDto dto);

    /**
     * 获取单条车辆
     *
     * @param id
     * @return
     */
    Map<String, Object> getTruckTeamById(Integer id);
}
