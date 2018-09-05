package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateListTruckQualificationDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckQualificationCriteriaDto;

import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckQualificationService {

    /**
     * 创建车队资质
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckQualification(CreateListTruckQualificationDto dto);

    /**
     * 分页查询待审核的运力资质
     *
     * @return
     */
    Map<String, Object> listQualification(ListTruckQualificationCriteriaDto criteriaDto);

    /**
     * 分页查询运力资质根据车队id
     *
     * @return
     */
    Map<String, Object> listQualificationByTruckIds(ListTruckQualificationCriteriaDto criteriaDto);
}
