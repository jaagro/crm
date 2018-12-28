package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettleRuleDto;
import org.apache.ibatis.annotations.Param;

/**
 * 客户合同结算配制CRUD扩展
 *
 * @author yj
 * @since 2018/12/24
 */
public interface CustomerContractSettleRuleMapperExt extends CustomerContractSettleRuleMapper {


    /**
     * 根据合同id查询不是历史纪录的
     *
     * @param contractId
     * @return
     */
    ReturnCustomerSettleRuleDto getByContractId(@Param("contractId") Integer contractId);
}
