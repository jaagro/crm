package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.truck.ListTruckQualificationCriteriaDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckQualificationDto;
import com.jaagro.crm.api.dto.response.truck.ReturnTruckQualificationDto;
import com.jaagro.crm.biz.entity.TruckQualification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gavin
 */
public interface TruckQualificationMapperExt extends TruckQualificationMapper {

    /**
     * 查询待审核的运力资质列表
     */
    List<ReturnTruckQualificationDto> listByCriteria(ListTruckQualificationCriteriaDto criteriaDto);

    /**
     * 待根据车队id获取审核运力资质列表
     *
     * @param criteriaDto
     * @return
     */
    List<ReturnTruckQualificationDto> listByIds(ListTruckQualificationCriteriaDto criteriaDto);

    /**
     * 根据车辆id查询运力资质列表
     */
    List<ListTruckQualificationDto> listByTruckId(@Param("truckId") Integer truckId);

    /**
     * 根据司机id查询资质列表
     *
     * @param driverId
     * @return
     */
    List<ListTruckQualificationDto> listByDriverId(@Param("driverId") Integer driverId);

    /**
     * 根据id获取审核运力资质
     *
     * @param id
     * @return
     */
    ReturnTruckQualificationDto getById(Integer id);

    /**
     * @param revalanceId
     * @param type        -- 1:个体车队 2:公司车队 3:车辆 4:司机
     * @return
     */
    Integer listCheckedByIdAndType(@Param("revalanceId") Integer revalanceId, @Param("type") Integer type);

    /**
     * @param revalanceId
     * @param type        -- 1:个体车队 2:公司车队 3:车辆 4:司机
     * @return
     */
    Integer listByIdAndType(TruckQualification truckQualification);

    /**
     * 逻辑删除 根据司机id
     *
     * @param driverId
     * @return
     */
    Integer disbaleByDriverId(@Param("driverId") Integer driverId);
}