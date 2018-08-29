package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckTeamContractDto;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckTeamContractService {

    /**
     * 创建车队合同
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

    /**
     * 根据合同编号查看合同
     */
    Map<String, Object> getByContractNumber(String contractNumber);

    /**
     * 修改车队合同
     *
     * @param dto
     * @return
     */
    Map<String, Object> updateTruckTeamContract(UpdateTruckTeamContractDto dto);

    /**
     * 创建车队合同关联关系
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeamContracts(List<CreateTruckTeamContractDto> dto, Integer truckTeamId);
}
