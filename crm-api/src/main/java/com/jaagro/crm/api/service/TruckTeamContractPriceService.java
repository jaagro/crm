package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractPriceDto;

import java.util.Map;

/**
 * 车队合同报价Service
 *
 * @author baiyiran
 */
public interface TruckTeamContractPriceService {

    /**
     * 新增
     *
     * @param createContractPriceDto
     * @return
     */
    Map<String, Object> createPrice(CreateTruckTeamContractPriceDto createContractPriceDto);

}
