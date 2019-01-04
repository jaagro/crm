package com.jaagro.crm.web.controller;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.socialDriver.ListDriverRegisterPurposeCriteriaDto;
import com.jaagro.crm.api.dto.request.socialDriver.UpdateSocialDriverRegisterPurposeDto;
import com.jaagro.crm.api.dto.response.socialDriverRegister.SocialDriverRegisterPurposeDto;
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
        log.info("O getByPhoneNumber phoneNumber={}",phoneNumber);
        SocialDriverRegisterPurposeDto socialDriverRegisterPurposeDto = socialDriverRegisterPurposeService.getByPhoneNumber(phoneNumber);
        if (socialDriverRegisterPurposeDto == null){
            return BaseResponse.queryDataEmpty();
        }
        return BaseResponse.successInstance(socialDriverRegisterPurposeDto);
    }

    @PostMapping("registerSendSMS")
    @ApiOperation("注册登录发送验证码")
    @Deprecated
    public BaseResponse registerSendSMS(@RequestParam("phoneNumber") String phoneNumber) {
        return BaseResponse.successInstance(socialDriverRegisterPurposeService.registerSendSMS(phoneNumber));
    }

    @PostMapping("/socialDriver")
    @ApiOperation("创建社会司机")
    public BaseResponse<Map<String,Object>> createSocialDriverByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber,@RequestParam("verificationCode") String verificationCode) {
        log.info("O createSocialDriverByPhoneNumber phoneNumber={},verificationCode={}",phoneNumber,verificationCode);
        Map<String,Object> result = socialDriverRegisterPurposeService.createSocialDriverByPhoneNumber(phoneNumber,verificationCode);
        return BaseResponse.successInstance(result);
    }

    @GetMapping("/socialDriverRegisterPurpose/{id}")
    @ApiOperation("根据id查询")
    public BaseResponse<SocialDriverRegisterPurposeDto> getSocialDriverRegisterPurposeDtoById(@PathVariable(value = "id") Integer id) {
        log.info("O getSocialDriverRegisterPurposeDtoById id={}",id);
        SocialDriverRegisterPurposeDto sdrDto = socialDriverRegisterPurposeService.getSocialDriverRegisterPurposeDtoById(id);
        if (sdrDto == null){
            return BaseResponse.idError();
        }
        return BaseResponse.successInstance(sdrDto);
    }

    @PutMapping("/socialDriverRegisterPurpose")
    @ApiOperation("加入平台")
    public BaseResponse updateSocialDriverRegisterPurpose(@RequestBody @Validated UpdateSocialDriverRegisterPurposeDto registerPurposeDto) {
        log.info("O updateSocialDriverRegisterPurpose registerPurposeDto={}",registerPurposeDto);
        socialDriverRegisterPurposeService.updateSocialDriverRegisterPurpose(registerPurposeDto);
        return BaseResponse.successInstance("加入平台成功");
    }

    @PostMapping("/listDriverRegisterPurposeByCriteria")
    @ApiOperation("查询社会司机注册意向列表")
    public BaseResponse<PageInfo<List<SocialDriverRegisterPurposeDto>>> listDriverRegisterPurposeByCriteria(@RequestBody @Validated ListDriverRegisterPurposeCriteriaDto criteria) {
        log.info("O listDriverRegisterPurposeByCriteria criteria={}",criteria);
        return BaseResponse.successInstance(socialDriverRegisterPurposeService.listDriverRegisterPurposeByCriteria(criteria));
    }

}
