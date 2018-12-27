package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.QuerySettleRuleDto;
import com.jaagro.crm.biz.entity.CustomerContractSettleTruckRule;

/**
 * 客户合同结算车辆配制CRUD扩展
 * @author yj
 * @since 2018/12/24
 */
public interface CustomerContractSettleTruckRuleMapperExt extends CustomerContractSettleTruckRuleMapper {

    CustomerContractSettleTruckRule getSettleTruckRuleByCriteria(QuerySettleRuleDto querySettleRuleDto);
}
