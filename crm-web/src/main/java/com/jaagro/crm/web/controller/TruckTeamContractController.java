package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.driver.CreateTruckTeamContractDto;
import com.jaagro.crm.api.dto.request.driver.UpdateTruckTeamContractDto;
import com.jaagro.crm.api.service.TruckTeamContractService;
import com.jaagro.crm.biz.mapper.TruckTeamContractMapper;
import com.jaagro.crm.biz.mapper.TruckTeamMapper;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author liqiangping
 */
@RestController
@Api(value = "truckTeamContract", description = "车队合同管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class TruckTeamContractController {

    @Autowired
    private TruckTeamContractService truckTeamContractService;

    @Autowired
    private TruckTeamContractMapper truckTeamContractMapper;

    @ApiOperation("新增合同")
    @PostMapping("/truckTeamContract")
    public BaseResponse insert(@RequestBody CreateTruckTeamContractDto dto){
        if(StringUtils.isEmpty(dto.getContractNumber())){
            return BaseResponse.errorInstance("合同编号不能为空");
        }
        if(StringUtils.isEmpty(dto.getContractDate())){
            return BaseResponse.errorInstance("签约日期不能为空");
        }
        return BaseResponse.service(truckTeamContractService.createTruckTeamContract(dto));
    }

    @ApiOperation("查询单个合同")
    @GetMapping("/truckTeamContract/{id}")
    public BaseResponse getById(@PathVariable Integer id) {
        if (truckTeamContractMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到合同ID");
        }
        Map<String, Object> result = truckTeamContractService.getById(id);
        return BaseResponse.service(result);
    }

    @ApiOperation("查询单个合同编号")
    @GetMapping("/truckTeamContractNumber/{contractNumber}")
    public BaseResponse getByContractNumber(@PathVariable String contractNumber) {
        if (truckTeamContractMapper.getByContractNumber(contractNumber) == null) {
            return BaseResponse.errorInstance("查询不到合同编号");
        }
        Map<String, Object> result = truckTeamContractService.getByContractNumber(contractNumber);
        return BaseResponse.service(result);
    }

    @ApiOperation("修改合同")
    @PostMapping("/updateTruckTeamContract")
    public BaseResponse updateTruckTeamContract(@RequestBody UpdateTruckTeamContractDto dto){
        if(StringUtils.isEmpty(dto.getContractNumber())){
            return BaseResponse.errorInstance("合同编号不能为空");
        }
        if(StringUtils.isEmpty(dto.getContractDate())){
            return BaseResponse.errorInstance("签约日期不能为空");
        }
        return BaseResponse.service(truckTeamContractService.updateTruckTeamContract(dto));
    }
}
