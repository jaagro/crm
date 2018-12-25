package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSettlePriceDto;

import java.util.Map;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
public interface CustomerContractSettlePriceService {

    /**
     * 创建客户合同报价
     *
     * @return
     */
    Map<String, Object> createCustomerSettlePrice(CreateCustomerSettlePriceDto settlePriceDto);
}
