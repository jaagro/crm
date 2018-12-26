package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSectionRuleDto;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
public interface CtmContractSettleSectionRuleService {

    /**
     * 创建里程区间配制
     *
     * @param sectionRuleDto
     * @return
     */
    Boolean createSectionRule(CreateCustomerSectionRuleDto sectionRuleDto);
}
