package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.driver.CreateTruckTeamDto;
import com.jaagro.crm.api.service.TruckTeamService;
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
@Api(value = "truckTeam", description = "车队管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class TruckTeamController {

    @Autowired
    private TruckTeamService truckTeamService;

    @ApiOperation("新增车队")
    @PostMapping("/truckTeam")
    public BaseResponse insert(@RequestBody CreateTruckTeamDto dto){
        if(StringUtils.isEmpty(dto.getTeamName())){
            return BaseResponse.errorInstance("车队名称不能为空");
        }
        if(StringUtils.isEmpty(dto.getCreditCode())){
            return BaseResponse.errorInstance("身份证不能为空");
        }
        return BaseResponse.service(truckTeamService.createTruckTeam(dto));
    }

    @ApiOperation("新增多个车队")
    @PostMapping("/truckTeams")
    public BaseResponse inserts(@RequestBody CreateTruckTeamDto dto){
        if(StringUtils.isEmpty(dto.getTeamName())){
            return BaseResponse.errorInstance("车队名称不能为空");
        }
        if(StringUtils.isEmpty(dto.getCreditCode())){
            return BaseResponse.errorInstance("身份证不能为空");
        }
        return BaseResponse.service(truckTeamService.createTruckTeams(dto));
    }
}
