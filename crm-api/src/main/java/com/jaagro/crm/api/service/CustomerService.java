package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.CreateCustomerDto;

import java.util.Map;

/**
 * @author liqiangping
 */
public interface CustomerService {
    /**
     * 创建客户
     * @param dto
     * @return
     */

    Map<String, Object> createCustomer(CreateCustomerDto dto);

}
