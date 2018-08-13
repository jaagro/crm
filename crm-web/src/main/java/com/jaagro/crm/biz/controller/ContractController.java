package com.jaagro.crm.biz.controller;

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
    public Map<String, Object> createContract(@RequestBody CreateContractDto dto) {

        if (StringUtils.isEmpty(dto.getCustomerId())) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同客户不能为空");
        }
        Map<String, Object> result = null;
        try {
            result = contractService.createContract(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), e.getMessage());
        }
        return result;
    }

    @ApiOperation("合同修改")
    @PutMapping("/contract")
    public Map<String, Object> updateContract(@RequestBody CreateContractDto dto) {

        if (contractMapper.selectByPrimaryKey(dto.getId()) == null) {
            return ServiceResult.error(ResponseStatusCode.ID_VALUE_NULL.getCode(), dto.getId() + " :合同不存在");
        }
        if (StringUtils.isEmpty(dto.getCustomerId())) {
            return ServiceResult.error(ResponseStatusCode.ID_VALUE_ERROR.getCode(), "合同客户不能为空");
        }
        Map<String, Object> result = null;
        try {
            result = contractService.updateContract(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), e.getMessage());
        }
        return result;
    }

    @ApiOperation("查询单个合同")
    @GetMapping("getContractByPK")
    @ApiImplicitParam(name = "id", value = "合同主键id", paramType = "query", required = true)
    public Map<String, Object> getContractByPK(Long id) {
        Map<String, Object> result = null;
        result = contractService.getContractByPk(id);
        return result;
    }

    @ApiOperation("分页查询合同")
    @PostMapping("getPage")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "起始页 [默认1]", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "分页大小[默认10]", paramType = "query", required = false)})
    public Map<String, Object> getContractByDto(@RequestBody ContractCriteriaDto dto) {
        return contractService.listByPage(dto);
    }

}
