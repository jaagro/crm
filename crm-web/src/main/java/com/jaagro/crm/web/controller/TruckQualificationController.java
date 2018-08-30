package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.truck.CreateListTruckQualificationDto;
import com.jaagro.crm.api.service.TruckQualificationService;
import com.jaagro.crm.biz.mapper.TruckTeamMapper;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
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
    private TruckTeamMapper truckTeamMapper;

    @ApiOperation("新增资质")
    @PostMapping("/truckQualification")
    public BaseResponse insert(@RequestBody CreateListTruckQualificationDto dto) {
        if (StringUtils.isEmpty(truckTeamMapper.selectByPrimaryKey(dto.getTruckTeamId()))) {
            return BaseResponse.errorInstance(dto.getTruckTeamId() + " :车队不存在");
        }
        if (StringUtils.isEmpty(dto.getQualification())) {
            return BaseResponse.errorInstance("请上传资质");
        }
        return BaseResponse.service(truckQualificationService.createTruckQualification(dto));
    }

    @ApiOperation("待审核资质分页")
    @PostMapping("/listQualification")
    public BaseResponse listQualification(@RequestBody CreateListTruckQualificationDto dto) {
        if (StringUtils.isEmpty(truckTeamMapper.selectByPrimaryKey(dto.getTruckTeamId()))) {
            return BaseResponse.errorInstance(dto.getTruckTeamId() + " :车队不存在");
        }
        if (StringUtils.isEmpty(dto.getQualification())) {
            return BaseResponse.errorInstance("请上传资质");
        }
        return BaseResponse.service(truckQualificationService.createTruckQualification(dto));
    }

}
