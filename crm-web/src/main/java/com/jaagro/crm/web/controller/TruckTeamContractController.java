package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckTeamContractCriteriaDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckTeamContractDto;
import com.jaagro.crm.api.service.TruckTeamContractService;
import com.jaagro.crm.biz.mapper.TruckTeamContractMapperExt;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
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
    private TruckTeamContractMapperExt truckTeamContractMapper;

    /**
     * 新增合同
     *
     * @param dto
     * @return
     */
    @ApiOperation("新增合同")
    @PostMapping("/truckTeamContract")
    public BaseResponse insert(@RequestBody CreateTruckTeamContractDto dto) {
        if (StringUtils.isEmpty(dto.getTruckTeamId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车队ID不能为空");
        }
        if (StringUtils.isEmpty(dto.getBussinessType())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "业务类型不能为空");
        }
        if (StringUtils.isEmpty(dto.getContractNumber())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同编号不能为空");
        }
        if (this.truckTeamContractMapper.getByContractNumber(dto.getContractNumber()) != null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同编号已存在");
        }
        if (StringUtils.isEmpty(dto.getStartDate())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同开始时间不能为空");
        }
        if (StringUtils.isEmpty(dto.getEndDate())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同结束时间不能为空");
        }
        Map<String, Object> resultMap;
        try {
            resultMap = truckTeamContractService.createTruckTeamContract(dto);
        } catch (Exception ex) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), ex.getMessage());
        }
        return BaseResponse.service(resultMap);
    }

    @ApiOperation("修改合同")
    @PostMapping("/updateTruckTeamContract")
    public BaseResponse updateTruckTeamContract(@RequestBody UpdateTruckTeamContractDto dto) {
        if (StringUtils.isEmpty(dto.getTruckTeamId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车队ID不能为空");
        }
        if (StringUtils.isEmpty(dto.getBussinessType())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "业务类型不能为空");
        }
        if (StringUtils.isEmpty(dto.getContractNumber())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同编号不能为空");
        }
        return BaseResponse.service(truckTeamContractService.updateTruckTeamContract(dto));
    }

    @ApiOperation("删除合同[逻辑]")
    @GetMapping("/deleteTeamContract/{id}")
    public BaseResponse deleteTeamContract(@PathVariable Integer id) {
        Map<String, Object> result = truckTeamContractService.disableContract(id);
        return BaseResponse.service(result);
    }

    @ApiOperation("查询单个合同")
    @GetMapping("/truckTeamContract/{id}")
    public BaseResponse getById(@PathVariable Integer id) {
        if (truckTeamContractMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "此合同不存在");
        }
        Map<String, Object> result = truckTeamContractService.getById(id);
        return BaseResponse.service(result);
    }

    @ApiOperation("查询单个合同编号")
    @GetMapping("/truckTeamContractNumber/{contractNumber}")
    public BaseResponse getByContractNumber(@PathVariable String contractNumber) {
        if (truckTeamContractMapper.getByContractNumber(contractNumber) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到合同编号");
        }
        Map<String, Object> result = truckTeamContractService.getByContractNumber(contractNumber);
        return BaseResponse.service(result);
    }

    @ApiOperation("分页查询合同")
    @PostMapping("/listTruckTeam")
    public BaseResponse listTruckTeam(@RequestBody ListTruckTeamContractCriteriaDto criteriaDto) {
        Map<String, Object> result = truckTeamContractService.listByCriteria(criteriaDto);
        return BaseResponse.service(result);
    }

}
