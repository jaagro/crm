package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettleTruckDto;
import com.jaagro.crm.api.service.CtmContractSettleTruckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author baiyiran
 * @Date 2018/12/26
 */
@Service
@Slf4j
public class CtmContractSettleTruckServiceImpl implements CtmContractSettleTruckService {
    /**
     * 创建车辆配制
     *
     * @param settleRuleDto
     * @return
     */
    @Override
    public Boolean createSettleTruck(CreateCustomerSettleTruckDto settleRuleDto) {
        return null;
    }
}
