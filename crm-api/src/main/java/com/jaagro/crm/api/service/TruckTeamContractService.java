package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.driver.CreateTruckTeamContractDto;

import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckTeamContractService {

    /**
     * 创建车队
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeamContract(CreateTruckTeamContractDto dto);

    /**
     * 获取单条合同
     *
     * @param id
     * @return
     */
    Map<String, Object> getById(Integer id);
}
