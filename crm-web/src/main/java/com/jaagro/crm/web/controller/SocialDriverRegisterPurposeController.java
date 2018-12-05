package com.jaagro.crm.web.controller;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.socialDriver.ListDriverRegisterPurposeCriteriaDto;
import com.jaagro.crm.api.dto.request.socialDriver.UpdateSocialDriverRegisterPurposeDto;
import com.jaagro.crm.api.dto.response.socialDriver.SocialDriverRegisterPurposeDto;
import com.jaagro.crm.api.service.SocialDriverRegisterPurposeService;
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
 * 社会司机注册意向管理
 *
 * @author yj
 * @since 2018/12/4
 */
@RestController
@Api(description = "社会司机注册意向管理", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class SocialDriverRegisterPurposeController {
    @Autowired
    private SocialDriverRegisterPurposeService socialDriverRegisterPurposeService;

    @GetMapping("/getByPhoneNumber")
    @ApiOperation("根据手机号查询社会司机")
    public BaseResponse<SocialDriverRegisterPurposeDto> getByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        SocialDriverRegisterPurposeDto socialDriverRegisterPurposeDto = socialDriverRegisterPurposeService.getByPhoneNumber(phoneNumber);
        return BaseResponse.successInstance(socialDriverRegisterPurposeDto);
    }

    @PostMapping("registerSendSMS")
    @ApiOperation("注册登录发送验证码")
    public BaseResponse registerSendSMS(@RequestParam("phoneNumber") String phoneNumber) {
        return BaseResponse.service(socialDriverRegisterPurposeService.registerSendSMS(phoneNumber));
    }

    @PostMapping("/socialDriver")
    @ApiOperation("创建社会司机")
    public BaseResponse createSocialDriverByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        socialDriverRegisterPurposeService.createSocialDriverByPhoneNumber(phoneNumber);
        return BaseResponse.successInstance("创建社会司机成功");
    }

    @GetMapping("/socialDriverRegisterPurpose/{id}")
    @ApiOperation("根据id查询")
    public BaseResponse<SocialDriverRegisterPurposeDto> getSocialDriverRegisterPurposeDtoById(@PathVariable(value = "id") Integer id){
        SocialDriverRegisterPurposeDto sdrDto = socialDriverRegisterPurposeService.getSocialDriverRegisterPurposeDtoById(id);
        return BaseResponse.successInstance(sdrDto);
    }

    @PutMapping("/socialDriverRegisterPurpose")
    @ApiOperation("加入平台")
    public BaseResponse updateSocialDriverRegisterPurpose(@RequestBody @Validated UpdateSocialDriverRegisterPurposeDto registerPurposeDto){
        socialDriverRegisterPurposeService.updateSocialDriverRegisterPurpose(registerPurposeDto);
        return BaseResponse.successInstance("加入平台成功");
    }

    @PostMapping("/listDriverRegisterPurposeByCriteria")
    @ApiOperation("查询社会司机注册意向列表")
    public BaseResponse<PageInfo<List<SocialDriverRegisterPurposeDto>>> listDriverRegisterPurposeByCriteria(@RequestBody @Validated ListDriverRegisterPurposeCriteriaDto criteria){
        return BaseResponse.successInstance(socialDriverRegisterPurposeService.listDriverRegisterPurposeByCriteria(criteria));
    }

}
