package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.truck.ListTruckCriteriaDto;
import com.jaagro.crm.api.dto.request.truck.QueryTruckDto;
import com.jaagro.crm.api.dto.response.truck.GetTruckDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckDto;
import com.jaagro.crm.api.dto.response.truck.ReturnCheckTruckDto;
import com.jaagro.crm.biz.entity.Truck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gavin
 */
public interface TruckMapperExt extends TruckMapper {

    /**
     * 查询车辆dto
     */
    GetTruckDto getTruckById(@Param("truckId") Integer truckId);

    /**
     * 通过车牌号码查询车辆
     *
     * @param truckNumber
     * @return
     */
    Truck getByTruckNumber(String truckNumber);

    /**
     * 删除车辆【逻辑】
     *
     * @param id
     * @param status
     * @return
     */
    int deleteTruckLogic(@Param("status") Integer status, @Param("id") Integer id);

    /**
     * 获取车辆list
     *
     * @param teamId
     * @param truckNumber
     * @return
     */
    List<GetTruckDto> listTruckByTeamId(@Param("teamId") Integer teamId, @Param("truckNumber") String truckNumber);

    /**
     * 运力管理
     *
     * @param criteria
     * @return
     */
    List<ListTruckDto> listTruckByCriteria(ListTruckCriteriaDto criteria);

    /**
     * 根据id获取待审核车辆信息
     *
     * @param id
     * @return
     */
    ReturnCheckTruckDto getCheckById(Integer id);

    /**
     * 列出可派的车辆（车队审核通过 、车队合同审核通过、车辆审核通过） gavin
     *
     * @param criteria
     * @return
     */
    List<ListTruckDto> listTruckForAssignWaybillByCriteria(QueryTruckDto criteria);

    /**
     * 根据车牌号模糊查询车辆id列表
     *
     * @param truckNumber
     * @return
     */
    List<Integer> getTruckIdsByTruckNum(@Param("truckNumber") String truckNumber);

    /**
     * 列出所有可派的车辆（车队审核通过 、车队合同审核通过、车辆审核通过） gavin
     * @return
     */
    List<ListTruckDto> listTruckForAssignWaybill();
}