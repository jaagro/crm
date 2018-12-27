package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.DriverContractSettleRule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

import com.jaagro.crm.api.dto.request.contract.DriverContractSettleCondition;

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

    /**
     * 根据车队合同id,车辆类型id,完成时间查询有效的司机合同结算配制
     *
     * @param truckTeamContractId
     * @param truckTypeId
     * @param doneDate
     * @return
     */
    DriverContractSettleRule selectEffectiveOne(@Param("truckTeamContractId") Integer truckTeamContractId, @Param("truckTypeId") Integer truckTypeId, @Param("doneDate") Date doneDate);
}
