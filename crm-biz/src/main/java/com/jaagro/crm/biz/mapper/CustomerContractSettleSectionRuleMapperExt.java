package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.QuerySettleRuleDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettleSectionRuleDto;
import com.jaagro.crm.biz.entity.CustomerContractSettleSectionRule;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户合同结算区间配制CRUD扩展
 *
 * @author yj
 * @since 2018/12/24
 */
public interface CustomerContractSettleSectionRuleMapperExt extends CustomerContractSettleSectionRuleMapper {

    /**
     * @param querySettleRuleDto
     * @return
     */
    CustomerContractSettleSectionRule getSettleSectionRuleByCriteria(QuerySettleRuleDto querySettleRuleDto);

    /**
     * 根据结算主表id查询
     *
     * @param ruleId
     * @return
     */
    List<ReturnCustomerSettleSectionRuleDto> listByRuleId(@Param("ruleId") Integer ruleId);

    /**
     * 根据合同id和里程数查询结算单价
     * @param customerContractId
     * @param mileage
     * @return
     */
    BigDecimal getPriceByMileageAndContractId(@Param("customerContractId") Integer customerContractId,@Param("mileage") BigDecimal mileage);
}
