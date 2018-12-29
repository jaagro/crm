package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.DriverContractSettleSectionRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 司机合同结算区间配制CRUD扩展
 *
 * @author yj
 * @since 2018/12/24
 */
public interface DriverContractSettleSectionRuleMapperExt extends DriverContractSettleSectionRuleMapper {
    /**
     * 根据司机合同结算配制表id查询
     *
     * @param driverContractSettleRuleId
     * @return
     */
    List<DriverContractSettleSectionRule> listByDriverContractSettleRuleId(Integer driverContractSettleRuleId);

    /**
     * 批量删除合同结算配制表
     *
     * @param ids
     */
    void deleteDriverContractSettleSectionById(@Param("ids") List<Integer> ids);
}
