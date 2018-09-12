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

    /**
     * 根据报价id逻辑删除阶梯报价
     *
     * @param id
     */
    Map<String, Object> disableByPriceId(Integer id);

    /**
     * 根据报价id删除阶梯报价
     *
     * @param id
     */
    Map<String, Object> deleteByPriceId(Integer id);
}
