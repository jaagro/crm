package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.CreateContractDto;
import com.jaagro.crm.api.dto.request.ContractCriteriaDto;
import com.jaagro.crm.api.service.ContractService;
import com.jaagro.crm.biz.mapper.ContractMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import utils.BaseResponse;
import utils.ResponseStatusCode;
import utils.ServiceResult;

import java.util.Map;

/**
 * @author liqiangping
 */
@RestController
@Api(value = "contract", description = "客户管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private ContractMapper contractMapper;

    @ApiOperation("合同新增")
    @PostMapping("/contract")
    public BaseResponse createContract(@RequestBody CreateContractDto dto) {

        if (StringUtils.isEmpty(dto.getCustomerId())) {
            return BaseResponse.idError("合同客户不能为空");
        }
        Map<String, Object> result;
        try {
            result = contractService.createContract(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errorInstance(e.getMessage());
        }
        return BaseResponse.service(result);
    }

    @ApiOperation("合同修改")
    @PutMapping("/contract")
    public BaseResponse updateContract(@RequestBody CreateContractDto dto) {

        if (contractMapper.selectByPrimaryKey(dto.getId()) == null) {
            return BaseResponse.idError("合同不存在");
        }
        if (StringUtils.isEmpty(dto.getCustomerId())) {
            return BaseResponse.idError("合同客户不能为空");
        }
        Map<String, Object> result;
        try {
            result = contractService.updateContract(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errorInstance(e.getMessage());
        }
        return BaseResponse.service(result);
    }

    @ApiOperation("查询单个合同")
    @GetMapping("/contract/{id}")
    public BaseResponse getById(@PathVariable Long id) {
        if (this.contractMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.queryDataEmpty();
        }
        Map<String, Object> result = contractService.getById(id);
        return BaseResponse.service(result);
    }

    @ApiOperation("分页查询合同")
    @PostMapping("/listByCriteria")
    public BaseResponse listByCriteria(@RequestBody ContractCriteriaDto dto) {
        return BaseResponse.service(contractService.listByCriteria(dto));
    }
}
