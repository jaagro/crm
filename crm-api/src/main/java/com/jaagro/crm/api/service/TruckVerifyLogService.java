package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateTruckVerifyLogDto;

import java.util.Map;

/**
 * @author baiyiran
 */
public interface TruckVerifyLogService {

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckVerifyLog(CreateTruckVerifyLogDto dto);

}
