package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.truck.ListDriverContractSettleDto;
import com.jaagro.crm.biz.entity.DriverContractSettleRule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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

    /**
     * 查询运力合同结算配置及报价区间表
     *
     * @param condition
     * @return
     */
    List<ListDriverContractSettleDto> listTruckTeamContractPriceCondition(DriverContractSettleCondition condition);

    /**
     * 查询非历史记录报价
     *
     * @param condition
     * @return
     */
    List<ListDriverContractSettleDto> listNonHistoryTruckTeamContractPriceCondition(DriverContractSettleCondition condition);

    /**
     * 司机合同结算配置
     * 批量删除
     *
     * @param ids
     */
    void deleteDriverContractSettleById(@Param("ids") List<Integer> ids);

    /**
     * 根据车队合同id查询司机合同报价信息
     *
     * @param truckTeamContractId
     * @return
     */
    List<ListDriverContractSettleDto> listByTruckTeamContractId(@Param("truckTeamContractId") Integer truckTeamContractId);
}
