package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
public interface ContractOilPriceService {

    /**
     * 创建油价配制
     *
     * @param createContractOilPriceDto
     * @return
     */
    Boolean createOilPrice(CreateContractOilPriceDto createContractOilPriceDto);
}
