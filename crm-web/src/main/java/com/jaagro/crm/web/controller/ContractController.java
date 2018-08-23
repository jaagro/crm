package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.contract.CreateContractDto;
import com.jaagro.crm.api.dto.request.contract.ListContractCriteriaDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractDto;
import com.jaagro.crm.api.service.ContractService;
import com.jaagro.crm.biz.mapper.CustomerContractMapper;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author baiyiran
 */
@RestController
@Api(value = "contract", description = "客户合同管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private CustomerContractMapper customerContractMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @ApiOperation("合同新增")
    @PostMapping("/contract")
    public BaseResponse createContract(@RequestBody CreateContractDto dto) {

        if (StringUtils.isEmpty(dto.getCustomerId())) {
            return BaseResponse.idNull("合同客户不能为空");
        }
        if (this.customerMapper.selectByPrimaryKey(dto.getCustomerId()) == null) {
            return BaseResponse.errorInstance("客户不存在");
        }
        UpdateContractDto updateContractDto = new UpdateContractDto();
        updateContractDto.setContractNumber(dto.getContractNumber());
        if (this.customerContractMapper.getByUpdateDto(updateContractDto) != null) {
            return BaseResponse.errorInstance("合同编号[contractumber]已存在");
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
    public BaseResponse updateContract(@RequestBody UpdateContractDto dto) {

        if (customerContractMapper.selectByPrimaryKey(dto.getId()) == null) {
            return BaseResponse.idError("合同不存在");
        }
        if (StringUtils.isEmpty(dto.getCustomerId())) {
            return BaseResponse.idError("合同客户不能为空");
        }
        if (this.customerContractMapper.getByUpdateDto(dto) != null) {
            return BaseResponse.errorInstance("合同编号[contractumber]已存在");
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
    public BaseResponse getById(@PathVariable Integer id) {
        if (this.customerContractMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.queryDataEmpty();
        }
        Map<String, Object> result = contractService.getById(id);
        return BaseResponse.service(result);
    }

    @ApiOperation("分页查询合同")
    @PostMapping("/listContractByCriteria")
    public BaseResponse listByCriteria(@RequestBody ListContractCriteriaDto dto) {
        return BaseResponse.service(contractService.listByCriteria(dto));
    }
}
