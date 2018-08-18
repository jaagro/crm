package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;
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
 * 客户管理
 *
 * @author baiyiran
 */
@RestController
@Api(value = "customer", description = "客户管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;

    @ApiOperation("新增客户")
    @PostMapping("/customer")
    public BaseResponse insertCustomer(@RequestBody CreateCustomerDto customer) {
        if (customer.getCustomerType() == null) {
            return BaseResponse.errorInstance("客户类型:[customerType]不能为空");
        }
        if (customer.getCustomerName() == null) {
            return BaseResponse.errorInstance("客户名称:[customerName]不能为空");
        }
        UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto();
        updateCustomerDto.setCustomerName(customer.getCustomerName());
        if (this.customerMapper.getByCustomerDto(updateCustomerDto) != null) {
            return BaseResponse.idNull("客户名称:[customerName]已存在");
        }
        return BaseResponse.service(customerService.createCustomer(customer));
    }

    @ApiOperation("删除客户[逻辑]")
    @DeleteMapping("/deleteCustomerById/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (this.customerMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到相应数据");
        }
        return BaseResponse.service(this.customerService.disableCustomer(id));
    }

    @ApiOperation("修改客户")
    @PutMapping("/customer")
    public BaseResponse updateCustomer(@RequestBody UpdateCustomerDto customer) {
        if (this.customerMapper.selectByPrimaryKey(customer.getId()) == null) {
            return BaseResponse.queryDataEmpty();
        }
        if (this.customerMapper.getByCustomerDto(customer) != null) {
            return BaseResponse.errorInstance("客户名称:[customerName]已存在");
        }
        return BaseResponse.service(customerService.updateById(customer));
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

    @ApiOperation("分页查询客户")
    @PostMapping("/listCustomerByCriteria")
    public BaseResponse listByCriteria(@RequestBody ListCustomerCriteriaDto criteriaDto) {
        return BaseResponse.service(this.customerService.listByCriteria(criteriaDto));
    }
}
