package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerDto;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import utils.BaseResponse;

import java.util.Map;

/**
 * @author liqiangping
 */
@RestController
@Api(value = "contract", description = "客户管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;

    @ApiOperation("新增客户")
    @PostMapping("/customer")
    public BaseResponse insertCustomer(@RequestBody CreateCustomerDto customer) {
        return BaseResponse.service(customerService.createCustomer(customer));
    }

    @ApiOperation("查询单个客户")
    @GetMapping("/customer/{id}")
    public BaseResponse getById(@PathVariable Long id) {
        if (this.customerMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.queryDataEmpty();
        }
        Map<String, Object> result = customerService.getById(id);
        return BaseResponse.service(result);
    }


}
