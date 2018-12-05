package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.response.socialDriver.SocialDriverRegisterPurposeDto;
import com.jaagro.crm.api.service.SocialDriverRegisterPurposeService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @ApiOperation("注册发送验证码")
    public BaseResponse registerSendSMS(@RequestParam("phoneNumber") String phoneNumber) {
        Map<String,String> result = socialDriverRegisterPurposeService.registerSendSMS(phoneNumber);
        return BaseResponse.service(result);
    }

    @PostMapping("/socialDriver")
    @ApiOperation("创建社会司机")
    public BaseResponse createSocialDriverByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        boolean success = socialDriverRegisterPurposeService.createSocialDriverByPhoneNumber(phoneNumber);
        if (success) {
            return BaseResponse.successInstance("创建社会司机成功");
        }
        return BaseResponse.errorInstance("创建社会司机失败");
    }

    @GetMapping("/socialDriverRegisterPurpose/{id}")
    public BaseResponse<SocialDriverRegisterPurposeDto> getSocialDriverRegisterPurposeDtoById(@PathVariable(value = "id") Integer id){
        SocialDriverRegisterPurposeDto sdrDto = socialDriverRegisterPurposeService.getSocialDriverRegisterPurposeDtoById(id);
        return BaseResponse.successInstance(sdrDto);
    }

}
