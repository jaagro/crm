package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSettleTruckRuleDto;

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
    Boolean createSettleTruck(CreateCustomerSettleTruckRuleDto settleRuleDto);
}
