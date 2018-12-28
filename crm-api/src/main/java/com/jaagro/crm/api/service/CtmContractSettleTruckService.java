package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettleTruckRuleDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettleTruckRuleDto;

import java.util.List;

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
    Boolean createSettleTruck(CreateCustomerSettleTruckRuleDto truckRuleDto);

    /**
     * 根据配置结算主表id获得列表
     *
     * @param id
     * @return
     */
    List<ReturnCustomerSettleTruckRuleDto> listByRuleId(Integer id);
}
