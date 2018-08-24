package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.driver.CreateTruckQualificationDto;

import java.util.List;
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
    Map<String, Object> createTruckQualification(List<CreateTruckQualificationDto> dto, Integer truckTeamId);

    /**
     * 创建车队资质
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckQualification(CreateTruckQualificationDto dto);
}
