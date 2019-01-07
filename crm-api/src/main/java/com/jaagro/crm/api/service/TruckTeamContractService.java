package com.jaagro.crm.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.contract.ContractOilPriceCondition;
import com.jaagro.crm.api.dto.request.contract.CreateDriverContractSettleDto;
import com.jaagro.crm.api.dto.request.contract.DriverContractSettleCondition;
import com.jaagro.crm.api.dto.request.contract.GetContractOilPriceDto;
import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;
import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckTeamContractCriteriaDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckTeamContractDto;
import com.jaagro.crm.api.dto.response.truck.ListDriverContractSettleDto;
import com.jaagro.crm.api.dto.response.truck.ListDriverContractSettleInfoFlagDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTypeDto;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckTeamContractService {

    /**
     * 创建车队合同
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeamContract(CreateTruckTeamContractDto dto);

    /**
     * 获取单条合同
     *
     * @param id
     * @return
     */
    Map<String, Object> getById(Integer id);

    /**
     * 根据合同编号查看合同
     */
    Map<String, Object> getByContractNumber(String contractNumber);

    /**
     * 修改车队合同
     *
     * @param dto
     * @return
     */
    Map<String, Object> updateTruckTeamContract(UpdateTruckTeamContractDto dto);

    /**
     * 创建车队合同关联关系
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeamContracts(List<CreateTruckTeamContractDto> dto, Integer truckTeamId);

    /**
     * 分页查询车队合同
     *
     * @param criteriaDto
     * @return
     */
    Map<String, Object> listByCriteria(ListTruckTeamContractCriteriaDto criteriaDto);

    /**
     * 逻辑删除
     *
     * @return
     */
    Map<String, Object> disableContract(Integer id);

    /**
     * 创建车队合同报价
     */
    void createTruckTeamContractPrice(CreateDriverContractSettleDto createDriverContractSettleDto);

    /**
     * 运力合同报价列表
     *
     * @param condition
     * @return
     */
    List<ListDriverContractSettleInfoFlagDto> listTruckTeamContractPrice(DriverContractSettleCondition condition);

    /**
     * 运力合同报价列表
     * @param condition
     * @return
     */
    ListDriverContractSettleInfoFlagDto listDriverContractPrice(DriverContractSettleCondition condition);

    /**
     * 运力合同报价详情
     *
     * @param condition
     * @return
     */
    ListDriverContractSettleDto listTruckTeamContractPriceDetails(DriverContractSettleCondition condition);

    /**
     * 当前车辆类型所有的报价历史记录
     *
     * @param condition
     * @return
     */
    PageInfo<ListDriverContractSettleDto> listTruckTeamContractPriceHistoryDetails(DriverContractSettleCondition condition);

    /**
     * 根据条件查询某一类车型
     *
     * @param goodType
     * @return
     */
    List<ListTruckTypeDto> listTruckTeamTypeByGoodType(Integer goodType);

    /**
     * 逻辑删除某一个类的车型
     *
     * @param condition
     */
    void deleteTeamContractPrice(DriverContractSettleCondition condition);

    /**
     * 获取最新的油价
     *
     * @return
     */
    GetContractOilPriceDto getNewOilPrice(ContractOilPriceCondition condition);

    /**
     * 更新油价
     *
     * @param createContractOilPriceDto
     */
    void updateOilPrice(CreateContractOilPriceDto createContractOilPriceDto);

}
