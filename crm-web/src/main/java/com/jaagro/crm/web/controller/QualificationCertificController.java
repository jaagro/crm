package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.customer.*;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.api.service.QualificationCertificService;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import com.jaagro.crm.biz.mapper.CustomerQualificationMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import utils.BaseResponse;

/**
 * 客户资质管理
 *
 * @author baiyiran
 */
@RestController
@Api(value = "qualificationCertific", description = "客户资质证件照管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class QualificationCertificController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private QualificationCertificService certificService;
    @Autowired
    private CustomerQualificationMapper certificMapper;

    @ApiOperation("新增资质")
    @PostMapping("/qualificationCertific")
    public BaseResponse insertCustomer(@RequestBody CreateCustomerQualificationDto certificDto) {
        if (certificDto.getCertificateType() == null) {
            return BaseResponse.idNull("证件类型:[certificateType]不能为空");
        }
        if (certificDto.getCertificateImageUrl() == null) {
            return BaseResponse.idNull("证件图片地址:[certificateImageUrl]不能为空");
        }
        if (certificDto.getCustomerId() == null) {
            return BaseResponse.idNull("客户id:[customerId]不能为空");
        }
        if (this.customerMapper.selectByPrimaryKey(certificDto.getCustomerId()) == null) {
            return BaseResponse.errorInstance("客户id:[customerId]不存在");
        }
        return BaseResponse.service(certificService.createQualificationCertific(certificDto));
    }

    @ApiOperation("删除资质[逻辑]")
    @DeleteMapping("/deleteQualificationCertificById/{id}")
    public BaseResponse deleteById(@PathVariable Integer id) {
        if (this.certificMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到相应数据");
        }
        return BaseResponse.service(this.certificService.disableQualificationCertific(id));
    }

    @ApiOperation("修改单个资质")
    @PutMapping("/qualificationCertific")
    public BaseResponse updateSite(@RequestBody UpdateCustomerQualificationDto certificDto) {
        if (certificDto.getCertificateType() == null) {
            return BaseResponse.idNull("证件类型:[certificateType]不能为空");
        }
        if (certificDto.getCertificateImageUrl() == null) {
            return BaseResponse.idNull("证件图片地址:[certificateImageUrl]不能为空");
        }
        if (certificDto.getCustomerId() == null) {
            return BaseResponse.idNull("客户id:[customerId]不能为空");
        }
        if (this.customerMapper.selectByPrimaryKey(certificDto.getCustomerId()) == null) {
            return BaseResponse.errorInstance("客户id:[customerId]不存在");
        }
        return BaseResponse.service(certificService.updateQualificationCertific(certificDto));
    }

    @ApiOperation("查询单个资质")
    @GetMapping("/qualificationCertific/{id}")
    public BaseResponse getById(@PathVariable Integer id) {
        if (this.certificMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.queryDataEmpty();
        }
        return BaseResponse.successInstance(this.certificMapper.selectByPrimaryKey(id));
    }

    @ApiOperation("分页查询资质")
    @PostMapping("/listQualificationCertificByCriteria")
    public BaseResponse listByCriteria(@RequestBody ListCustomerQualificationCriteriaDto criteriaDto) {
        return BaseResponse.service(this.certificService.listByCriteria(criteriaDto));
    }
}
