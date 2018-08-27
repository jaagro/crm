package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.contract.CreateContractDto;
import com.jaagro.crm.api.dto.request.contract.ListContractCriteriaDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractQualificationDto;
import com.jaagro.crm.api.service.ContractQualificationService;
import com.jaagro.crm.api.service.ContractService;
import com.jaagro.crm.biz.entity.ContractQualification;
import com.jaagro.crm.biz.mapper.ContractQualificationMapper;
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
    @Autowired
    private ContractQualificationMapper qualificationMapper;
    @Autowired
    private ContractQualificationService contractQualificationService;

    @ApiOperation("合同新增")
    @PostMapping("/contract")
    public BaseResponse createContract(@RequestBody CreateContractDto dto) {
        if (StringUtils.isEmpty(dto.getCustomerId())) {
            return BaseResponse.idNull("客户id:[customerId]不能为空");
        }
        if (this.customerMapper.selectByPrimaryKey(dto.getCustomerId()) == null) {
            return BaseResponse.errorInstance("客户id:[customerId]不存在");
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

    //-------------------------------------------合同资质-------------------------------------------------
    @ApiOperation("合同资质修改")
    @PutMapping("/ContractQualification")
    public BaseResponse updateContractQualification(@RequestBody UpdateContractQualificationDto dto) {
        if (customerContractMapper.selectByPrimaryKey(dto.getId()) == null) {
            return BaseResponse.idError("合同资质不存在");
        }
        if (StringUtils.isEmpty(dto.getRelevanceId())) {
            return BaseResponse.idError("合同关联id不能为空");
        }
        Map<String, Object> result;
        try {
            result = contractQualificationService.updateContractQuaion(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errorInstance(e.getMessage());
        }
        return BaseResponse.service(result);
    }

    @ApiOperation("[逻辑]删除合同资质")
    @GetMapping("/disableContractQualification/{id}")
    public BaseResponse disContractQualificationById(@PathVariable Integer id) {
        if (this.qualificationMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到相应信息");
        }
        return BaseResponse.service(contractQualificationService.disableContractQuaion(id));
    }

    @ApiOperation("删除合同资质")
    @GetMapping("/deleteContractQualification/{id}")
    public BaseResponse deleteContractQualificationById(@PathVariable Integer id) {
        if (this.qualificationMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到相应信息");
        }
        int result = qualificationMapper.deleteByPrimaryKey(id);
        if (result > 0) {
            return BaseResponse.successInstance("删除成功");
        } else {
            return BaseResponse.errorInstance("删除失败");
        }

    }

    @ApiOperation("查询单个合同")
    @GetMapping("/ContractQualification/{id}")
    public BaseResponse getContractQualificationById(@PathVariable Integer id) {
        if (this.customerContractMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到相应信息");
        }
        ContractQualification qualification = qualificationMapper.selectByPrimaryKey(id);
        return BaseResponse.successInstance(qualification);
    }

    @ApiOperation("根据合同id查询资质")
    @PostMapping("/listConQualfctnByContractId")
    public BaseResponse listConQualfctnByContractId(@PathVariable Integer contractId) {
        return BaseResponse.successInstance(qualificationMapper.listByContractId(contractId));
    }
}
