package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSettlePriceDto;
import com.jaagro.crm.api.service.CustomerContractSettlePriceService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
@Service
public class CustomerContractSettlePriceServiceImpl implements CustomerContractSettlePriceService {
    /**
     * 创建客户合同报价
     *
     * @param settlePriceDto
     * @return
     */
    @Override
    public Map<String, Object> createCustomerSettlePrice(CreateCustomerSettlePriceDto settlePriceDto) {
        return null;
    }
}
