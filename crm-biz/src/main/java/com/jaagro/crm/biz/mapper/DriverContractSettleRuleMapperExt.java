package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.DriverContractSettleCondition;
import com.jaagro.crm.biz.entity.DriverContractSettleRule;

import java.util.List;

/**
 * 司机合同结算配制CRUD扩展
 *
 * @author yj
 * @since 2018/12/24
 */
public interface DriverContractSettleRuleMapperExt extends DriverContractSettleRuleMapper {

    /**
     * 根据条件查询司机合同结算配置
     *
     * @param condition
     * @return
     * @author @Gao.
     */
    DriverContractSettleRule listDriverContractSettleRuleByCondition(DriverContractSettleCondition condition);

}
