package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettleTruckDto;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
public interface CtmContractSettleTruckService {

    /**
     * 创建车辆配制
     *
     * @param settleRuleDto
     * @return
     */
    Boolean createSettleTruck(CreateCustomerSettleTruckDto settleRuleDto);
}
