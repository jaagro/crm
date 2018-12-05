package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.response.socialDriver.SocialDriverRegisterPurposeDto;
import com.jaagro.crm.api.service.SocialDriverRegisterPurposeService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation("查询是否存在社会司机")
    public BaseResponse<SocialDriverRegisterPurposeDto> getByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber){
        SocialDriverRegisterPurposeDto socialDriverRegisterPurposeDto = socialDriverRegisterPurposeService.getByPhoneNumber(phoneNumber);
        return BaseResponse.successInstance(socialDriverRegisterPurposeDto);
    }
}
