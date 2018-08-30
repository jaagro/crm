package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContactsDto;
import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamDto;
import com.jaagro.crm.api.service.TruckTeamContactsService;
import com.jaagro.crm.api.service.TruckTeamService;
import com.jaagro.crm.biz.mapper.TruckTeamMapper;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
@RestController
@Api(value = "truckTeam", description = "车队管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class TruckTeamController {

    @Autowired
    private TruckTeamService truckTeamService;
    @Autowired
    private TruckTeamMapper truckTeamMapper;
    @Autowired
    private TruckTeamContactsService truckTeamContactsService;

    @ApiOperation("新增车队")
    @PostMapping("/truckTeam")
    public BaseResponse insertTruckTeam(@RequestBody CreateTruckTeamDto truckTeam){
        if(StringUtils.isEmpty(truckTeam.getTeamName())){
            return BaseResponse.errorInstance("车队名称不能为空");
        }
        return BaseResponse.service(truckTeamService.createTruckTeam(truckTeam));
    }

    @ApiOperation("车队修改")
    @PutMapping("/truckTeam")
    public BaseResponse updateTruckTeam(@RequestBody CreateTruckTeamDto truckTeam){
        if(StringUtils.isEmpty(truckTeam.getId())){
            return BaseResponse.idError("id");
        }
        return BaseResponse.service(truckTeamService.updateTruckTeam(truckTeam));
    }

    @ApiOperation("查询单个车队")
    @GetMapping("/truckTeam/{id}")
    public BaseResponse getTruckTeamById(@PathVariable Integer id) {
        Map<String, Object> result = truckTeamService.getTruckTeamById(id);
        return BaseResponse.service(result);
    }

    @ApiOperation("删除车队【逻辑】")
    @DeleteMapping("/truckTeam/{id}")
    public BaseResponse deleteTruckTeam(@PathVariable("id") Integer id){
        return BaseResponse.service(truckTeamService.deleteTruckTeam(id));
    }

    @ApiOperation("新增车队联系人")
    @PostMapping("/truckTeamContacts")
    public BaseResponse createTruckTeamContacts(@RequestBody List<CreateTruckTeamContactsDto> contacts){
        return BaseResponse.service(truckTeamContactsService.createTruckTeamContacts(contacts));
    }

    @ApiOperation("获取车队联系人列表")
    @GetMapping("/listTruckTeamContacts/{teamId}")
    public BaseResponse listTruckTeamContacts(@PathVariable("teamId") Integer teamId){
        return BaseResponse.service(truckTeamContactsService.listTruckTeamContacts(teamId));
    }

}
