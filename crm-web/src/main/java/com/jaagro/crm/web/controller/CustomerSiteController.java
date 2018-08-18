package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerSiteDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.api.service.CustomerSiteService;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import com.jaagro.crm.biz.mapper.CustomerSiteMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import utils.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * 客户地址管理
 *
 * @author baiyiran
 */
@RestController
@Api(value = "customer", description = "客户地址管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerSiteController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerSiteService siteService;
    @Autowired
    private CustomerSiteMapper siteMapper;

    @ApiOperation("新增单个地址")
    @PostMapping("/site")
    public BaseResponse insertCustomer(@RequestBody CreateCustomerSiteDto siteDto) {
        if (siteDto.getCustomerId() == null) {
            return BaseResponse.idNull("客户id:[customerId]不能为空");
        }
        if (siteDto.getSiteType() == null) {
            return BaseResponse.idNull("地址类型:[siteType]不能为空");
        }
        if (siteDto.getSiteName() == null) {
            return BaseResponse.idNull("地址名称:[siteName]不能为空");
        }
        if(this.siteMapper.getSiteName(siteDto.getSiteName()) != null){
            return BaseResponse.errorInstance("地址名称:[siteName]已存在");
        }
        return BaseResponse.service(siteService.createSite(siteDto));
    }

    @ApiOperation("删除单个地址[逻辑]")
    @DeleteMapping("/deleteSiteById/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (this.siteMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到相应数据");
        }
        return BaseResponse.service(this.siteService.disableSite(id));
    }

//    @ApiOperation("删除多个地址[逻辑]")
//    @DeleteMapping("/deleteById/{id}")
//    public BaseResponse deleteById(@PathVariable List<CustomerSiteReturnDto> siteReturnDtos) {
//        if(siteReturnDtos != null && siteReturnDtos.size() > 0){
//            this.siteService.updateSite(siteReturnDtos);
//        }else{
//
//        }
//        if (this.customerMapper.selectByPrimaryKey(id) == null) {
//            return BaseResponse.errorInstance("查询不到相应数据");
//        }
//        return BaseResponse.service(this.customerService.disableCustomer(id));
//    }

//    @ApiOperation("修改单个地址")
//    @PutMapping("/site")
//    public BaseResponse updateCustomer(@RequestBody UpdateCustomerDto customer) {
//        if (this.customerMapper.selectByPrimaryKey(customer.getId()) == null) {
//            return BaseResponse.queryDataEmpty();
//        }
//        return BaseResponse.service(customerService.updateById(customer));
//    }

    @ApiOperation("修改多个地址")
    @PutMapping("/site")
    public BaseResponse updateCustomer(@RequestBody UpdateCustomerDto customer) {
        if (this.customerMapper.selectByPrimaryKey(customer.getId()) == null) {
            return BaseResponse.queryDataEmpty();
        }
        return BaseResponse.service(customerService.updateById(customer));
    }

    @ApiOperation("查询单个地址")
    @GetMapping("/site/{id}")
    public BaseResponse getById(@PathVariable Long id) {
        if (this.customerMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.queryDataEmpty();
        }
        Map<String, Object> result = customerService.getById(id);
        return BaseResponse.service(result);
    }

    @ApiOperation("分页查询地址")
    @PostMapping("/listSiteByCriteria")
    public BaseResponse listByCriteria(@RequestBody ListCustomerCriteriaDto criteriaDto) {
        return BaseResponse.service(this.customerService.listByCriteria(criteriaDto));
    }
}
