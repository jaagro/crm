package com.jaagro.crm.web.controller;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.customerRegister.ListCustomerRegisterPurposeCriteriaDto;
import com.jaagro.crm.api.dto.request.customerRegister.UpdateCustomerRegisterPurposeDto;
import com.jaagro.crm.api.dto.response.customerRegister.CustomerRegisterPurposeDto;
import com.jaagro.crm.api.service.CustomerRegisterPurposeService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户注册意向管理
 *
 * @author yj
 * @since 2018/12/13
 */
@RestController
@Api(description = "客户注册意向管理", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CustomerRegisterPurposeController {
    @Autowired
    private CustomerRegisterPurposeService customerRegisterPurposeService;

    @GetMapping("/getCustomerRegisterPurposeByPhoneNumber")
    @ApiOperation("根据手机号查询客户注册意向")
    public BaseResponse<CustomerRegisterPurposeDto> getCustomerRegisterPurposeByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        log.info("O getCustomerRegisterPurposeByPhoneNumber phoneNumber={}", phoneNumber);
        CustomerRegisterPurposeDto customerRegisterPurposeDto = customerRegisterPurposeService.getByPhoneNumber(phoneNumber);
        if (customerRegisterPurposeDto == null) {
            return BaseResponse.queryDataEmpty();
        }
        return BaseResponse.successInstance(customerRegisterPurposeDto);
    }

    @PostMapping("/customerRegister")
    @ApiOperation("创建客户")
    public BaseResponse<Integer> createCustomerRegisterByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("verificationCode") String verificationCode) {
        log.info("O createCustomerRegisterByPhoneNumber phoneNumber={},verificationCode={}", phoneNumber, verificationCode);
        Integer id = customerRegisterPurposeService.createCustomerRegisterByPhoneNumber(phoneNumber, verificationCode);
        return BaseResponse.successInstance(id);
    }

    @GetMapping("/customerRegisterPurpose/{id}")
    @ApiOperation("根据id查询")
    public BaseResponse<CustomerRegisterPurposeDto> getCustomerRegisterPurposeDtoById(@PathVariable(value = "id") Integer id) {
        log.info("O getCustomerRegisterPurposeDtoById id={}", id);
        CustomerRegisterPurposeDto customerRegisterPurposeDto = customerRegisterPurposeService.getCustomerRegisterPurposeDtoById(id);
        if (customerRegisterPurposeDto == null) {
            return BaseResponse.idError();
        }
        return BaseResponse.successInstance(customerRegisterPurposeDto);
    }

    @PutMapping("/customerRegisterPurpose")
    @ApiOperation("我要运输")
    public BaseResponse updateCustomerRegisterPurpose(@RequestBody @Validated UpdateCustomerRegisterPurposeDto registerPurposeDto) {
        log.info("O updateCustomerRegisterPurpose registerPurposeDto={}", registerPurposeDto);
        customerRegisterPurposeService.updateCustomerRegisterPurpose(registerPurposeDto);
        return BaseResponse.successInstance("更新信息成功");
    }

    @PostMapping("/listCustomerRegisterPurposeByCriteria")
    @ApiOperation("查询客户注册意向列表")
    public BaseResponse<PageInfo<List<CustomerRegisterPurposeDto>>> listCustomerRegisterPurposeByCriteria(@RequestBody @Validated ListCustomerRegisterPurposeCriteriaDto criteria) {
        return BaseResponse.successInstance(customerRegisterPurposeService.listCustomerRegisterPurposeByCriteria(criteria));
    }
}
