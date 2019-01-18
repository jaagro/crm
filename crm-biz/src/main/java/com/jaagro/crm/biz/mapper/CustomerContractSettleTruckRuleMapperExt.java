package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.QuerySettleRuleDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettleTruckRuleDto;
import com.jaagro.crm.biz.entity.CustomerContractSettleTruckRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户合同结算车辆配制CRUD扩展
 *
 * @author yj
 * @since 2018/12/24
 */
public interface CustomerContractSettleTruckRuleMapperExt extends CustomerContractSettleTruckRuleMapper {

    CustomerContractSettleTruckRule getSettleTruckRuleByCriteria(QuerySettleRuleDto querySettleRuleDto);

    /**
     * 根据结算主表id查询
     *
     * @param ruleId
     * @return
     */
    List<ReturnCustomerSettleTruckRuleDto> listByRuleId(@Param("ruleId") Integer ruleId);

    /**
     * 合同报价根据车型获取价格基数
     * @param customerContractId
     * @param truckTypeId
     * @return
     */
    CustomerContractSettleTruckRule getLatestTruckRule(@Param("customerContractId") Integer customerContractId, @Param("truckTypeId") Integer truckTypeId);
}
