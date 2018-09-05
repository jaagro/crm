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

    /**
     * 根据合同id逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableByContractId(Integer id);

    /**
     * 根据合同id删除
     *
     * @param id
     * @return
     */
    Map<String, Object> deleteByContractId(Integer id);
}
