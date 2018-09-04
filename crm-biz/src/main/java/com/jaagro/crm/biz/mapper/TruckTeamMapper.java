package com.jaagro.crm.biz.mapper;


import com.jaagro.crm.api.dto.response.truck.ReturnCheckTruckTeamDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTeamDto;
import com.jaagro.crm.biz.entity.TruckTeam;

public interface TruckTeamMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(TruckTeam record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(TruckTeam record);

    /**
     * @mbggenerated 2018-08-23
     */
    TruckTeam selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(TruckTeam record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(TruckTeam record);

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
}