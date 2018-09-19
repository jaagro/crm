package com.jaagro.crm.biz.mapper;


import com.jaagro.crm.api.dto.request.truck.ListTruckTeamCriteriaDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTeamDto;
import com.jaagro.crm.api.dto.response.truck.ReturnCheckTruckTeamDto;

import java.util.List;

/**
 * @author gavin
 */
public interface TruckTeamMapperExt extends TruckTeamMapper {

    /**
     * 查询车辆dto
     */
    ListTruckTeamDto getTruckTeamById(Integer id);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    int deleteByLogic(Integer id);

    /**
     * 获得审核列表所需字段
     *
     * @return
     */
    ReturnCheckTruckTeamDto getTeamById(Integer teamId);

    /**
     * 分页查询车队
     *
     * @param truckCriteria
     */
    List<ListTruckTeamDto> listByCeriteria(ListTruckTeamCriteriaDto truckCriteria);

}