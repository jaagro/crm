package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.QuerySettleRuleDto;
import com.jaagro.crm.biz.entity.CustomerContractSettleSectionRule;

/**
 * 客户合同结算区间配制CRUD扩展
 * @author yj
 * @since 2018/12/24
 */
public interface CustomerContractSettleSectionRuleMapperExt extends CustomerContractSettleSectionRuleMapper {
    CustomerContractSettleSectionRule  getSettleSectionRuleByCriteria(QuerySettleRuleDto querySettleRuleDto);
}
