package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.truck.GetTruckDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckDto;
import com.jaagro.crm.api.dto.response.truck.ReturnCheckTruckDto;
import com.jaagro.crm.biz.entity.Truck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TruckMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(Truck record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(Truck record);

    /**
     * @mbggenerated 2018-08-23
     */
    Truck selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(Truck record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(Truck record);

    /**
     * 查询车辆dto
     */
    GetTruckDto getTruckById(Integer id);

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
    List<ListTruckDto> listTruckByTeamId(@Param("teamId") Integer teamId, @Param("truckNumber") String truckNumber);

    /**
     * 根据id获取待审核车辆信息
     *
     * @param id
     * @return
     */
    ReturnCheckTruckDto getCheckById(Integer id);
}