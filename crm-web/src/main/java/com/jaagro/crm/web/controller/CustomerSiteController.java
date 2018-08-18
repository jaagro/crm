package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.customer.*;
import com.jaagro.crm.api.dto.response.customer.CustomerSiteReturnDto;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.api.service.CustomerSiteService;
import com.jaagro.crm.biz.entity.CustomerSite;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import com.jaagro.crm.biz.mapper.CustomerSiteMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import utils.BaseResponse;
import utils.ServiceResult;

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
            return BaseResponse.errorInstance("地址类型:[siteType]不能为空");
        }
        if (siteDto.getSiteName() == null) {
            return BaseResponse.errorInstance("地址名称:[siteName]不能为空");
        }
        UpdateCustomerSiteDto customerSiteDto = new UpdateCustomerSiteDto();
        customerSiteDto.setSiteName(siteDto.getSiteName());
        CustomerSiteReturnDto site = this.siteMapper.getSiteDto(customerSiteDto);
        if (site != null) {
            return BaseResponse.errorInstance("地址名称:[siteName]已存在");
        }
        if (this.customerMapper.selectByPrimaryKey(siteDto.getCustomerId()) == null) {
            return BaseResponse.errorInstance("客户id:[customerId]不存在");
        }
        return BaseResponse.service(siteService.createSite(siteDto));
    }

    @ApiOperation("删除地址[逻辑]")
    @DeleteMapping("/deleteSiteById/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (this.siteMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到相应数据");
        }
        return BaseResponse.service(this.siteService.disableSite(id));
    }

    @ApiOperation("修改单个地址")
    @PutMapping("/site")
    public BaseResponse updateSite(@RequestBody UpdateCustomerSiteDto siteDto) {
        if (siteDto.getCustomerId() == null) {
            return BaseResponse.idNull("客户id:[customerId]不能为空");
        }
        if (siteDto.getSiteType() == null) {
            return BaseResponse.errorInstance("地址类型:[siteType]不能为空");
        }
        if (siteDto.getSiteName() == null) {
            return BaseResponse.errorInstance("地址名称:[siteName]不能为空");
        }
        if (this.siteMapper.getSiteDto(siteDto) != null) {
            return BaseResponse.errorInstance("地址名称:[siteName]已存在");
        }
        if (this.customerMapper.selectByPrimaryKey(siteDto.getCustomerId()) == null) {
            return BaseResponse.errorInstance("客户id:[customerId]不存在");
        }
        if (this.siteMapper.selectByPrimaryKey(siteDto.getId()) == null) {
            return BaseResponse.queryDataEmpty();
        }
        return BaseResponse.service(siteService.updateSite(siteDto));
    }

    @ApiOperation("查询单个地址")
    @GetMapping("/site/{id}")
    public BaseResponse getById(@PathVariable Long id) {
        if (this.siteMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.queryDataEmpty();
        }
        return BaseResponse.successInstance(this.siteMapper.selectByPrimaryKey(id));
    }

    @ApiOperation("分页查询地址")
    @PostMapping("/listSiteByCriteria")
    public BaseResponse listByCriteria(@RequestBody ListSiteCriteriaDto criteriaDto) {
        return BaseResponse.service(this.siteService.listByCriteria(criteriaDto));
    }
}
