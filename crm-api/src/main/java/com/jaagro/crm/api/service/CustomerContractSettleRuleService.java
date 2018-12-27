package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSettleRuleDto;

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
}
