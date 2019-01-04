package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettleSectionRuleDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettleSectionRuleDto;

import java.util.List;

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
    Boolean createSectionRule(CreateCustomerSettleSectionRuleDto sectionRuleDto);

    /**
     * 根据配置结算主表id获得列表
     *
     * @param id
     * @return
     */
    List<ReturnCustomerSettleSectionRuleDto> listByRuleId(Integer id);
}
