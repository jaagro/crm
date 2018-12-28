package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettleRuleDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettleRuleDto;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
public interface CustomerContractSettleRuleService {

    /**
     * 创建结算配制
     *
     * @param settleRuleDto
     * @return
     */
    Boolean createSettleRule(CreateCustomerSettleRuleDto settleRuleDto);

    /**
     * 根据合同id获得结算
     *
     * @param contractId
     * @return
     */
    ReturnCustomerSettleRuleDto getByContractId(Integer contractId);
}
