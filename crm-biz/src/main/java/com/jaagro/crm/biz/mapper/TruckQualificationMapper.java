package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.truck.ListTruckQualificationCriteriaDto;
import com.jaagro.crm.api.dto.response.truck.ReturnTruckQualificationDto;
import com.jaagro.crm.biz.entity.TruckQualification;

import java.util.List;

public interface TruckQualificationMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(TruckQualification record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(TruckQualification record);

    /**
     * @mbggenerated 2018-08-23
     */
    TruckQualification selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(TruckQualification record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(TruckQualification record);

    /**
     * 查询待审核的运力资质列表
     */
    List<ReturnTruckQualificationDto> listByCriteria(ListTruckQualificationCriteriaDto criteriaDto);

    /**
     * 待根据车队id获取审核运力资质列表
     *
     * @param teamId
     * @return
     */
    List<ReturnTruckQualificationDto> listByTeamId(Integer truckTeamId);
}