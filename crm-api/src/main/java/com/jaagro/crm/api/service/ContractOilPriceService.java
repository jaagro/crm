package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerOilPriceDto;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
public interface ContractOilPriceService {

    /**
     * 创建油价配制
     *
     * @param createCustomerOilPriceDto
     * @return
     */
    Boolean createOilPrice(CreateCustomerOilPriceDto createCustomerOilPriceDto);
}
