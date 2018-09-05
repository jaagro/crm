package com.jaagro.crm.api.service;


import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractSectionPriceDto;

import java.util.Map;

/**
 * @author baiyiran
 */
public interface TruckTeamContractSectionPriceService {

    /**
     * 新增
     *
     * @param contractSectionPriceDto
     * @return
     */
    Map<String, Object> createSectionPrice(CreateTruckTeamContractSectionPriceDto contractSectionPriceDto);
}
