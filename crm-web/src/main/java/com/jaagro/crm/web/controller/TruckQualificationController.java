package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;
import com.jaagro.crm.api.dto.request.driver.CreateTruckQualificationDto;
import com.jaagro.crm.api.service.TruckQualificationService;
import com.jaagro.crm.biz.mapper.TruckQualificationMapper;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqiangping
 */
@RestController
@Api(value = "truckQualification", description = "车队资质管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class TruckQualificationController {

    @Autowired
    private TruckQualificationService truckQualificationService;
    @Autowired
    private TruckQualificationMapper truckQualificationMapper;

    @ApiOperation("新增资质")
    @PostMapping("/truckQualification")
    public BaseResponse insert(@RequestBody CreateTruckQualificationDto dto){
        if(StringUtils.isEmpty(dto.getCertificateType())){
            return BaseResponse.errorInstance("资质类型不能为空");
        }
        return BaseResponse.service(truckQualificationService.createTruckQualification(dto));
    }


}
