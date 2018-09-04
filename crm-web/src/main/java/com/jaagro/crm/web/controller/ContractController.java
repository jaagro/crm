package com.jaagro.crm.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.contract.*;
import com.jaagro.crm.api.dto.request.customer.CreateQualificationVerifyLogDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCheckContractQualificationDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractDto;
import com.jaagro.crm.api.service.ContractQualificationService;
import com.jaagro.crm.api.service.ContractService;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.api.service.QualificationVerifyLogService;
import com.jaagro.crm.biz.entity.ContractQualification;
import com.jaagro.crm.biz.mapper.ContractQualificationMapper;
import com.jaagro.crm.biz.mapper.CustomerContractMapper;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @Autowired
    private CustomerService customerService;
    @Autowired
    private QualificationVerifyLogService logService;

    /**
     * 合同新增
     *
     * @param dto
     * @return
     */
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

    /**
     * 合同修改
     *
     * @param dto
     * @return
     */
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

    /**
     * 查询单个合同
     *
     * @param id
     * @return
     */
    @ApiOperation("查询单个合同")
    @GetMapping("/contract/{id}")
    public BaseResponse getById(@PathVariable Integer id) {
        if (this.customerContractMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.queryDataEmpty();
        }
        Map<String, Object> result = contractService.getById(id);
        return BaseResponse.service(result);
    }

    /**
     * 分页查询合同
     *
     * @param dto
     * @return
     */
    @ApiOperation("分页查询合同")
    @PostMapping("/listContractByCriteria")
    public BaseResponse listByCriteria(@RequestBody ListContractCriteriaDto dto) {
        return BaseResponse.service(contractService.listByCriteria(dto));
    }

    //----------------------------------------------合同资质-------------------------------------------------

    /**
     * 合同资质修改
     *
     * @param dto
     * @return
     */
    @ApiOperation("合同资质修改")
    @PutMapping("/ContractQualification")
    public BaseResponse updateContractQualification(@RequestBody UpdateContractQualificationDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return BaseResponse.idError("合同资质id不能为空");
        }
        if (customerContractMapper.selectByPrimaryKey(dto.getId()) == null) {
            return BaseResponse.idError("合同资质不存在");
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

    /**
     * [逻辑]删除合同资质
     *
     * @param id
     * @return
     */
    @ApiOperation("[逻辑]删除合同资质")
    @GetMapping("/disableContractQualification/{id}")
    public BaseResponse disContractQualificationById(@PathVariable Integer id) {
        if (this.qualificationMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到相应信息");
        }
        return BaseResponse.service(contractQualificationService.disableContractQuaion(id));
    }

    /**
     * 删除合同资质
     *
     * @param id
     * @return
     */
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

    /**
     * 查询单个合同
     *
     * @param id
     * @return
     */
    @ApiOperation("查询单个合同")
    @GetMapping("/ContractQualification/{id}")
    public BaseResponse getContractQualificationById(@PathVariable Integer id) {
        if (this.customerContractMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到相应信息");
        }
        ContractQualification qualification = qualificationMapper.selectByPrimaryKey(id);
        return BaseResponse.successInstance(qualification);
    }

    /**
     * 根据合同id查询资质
     *
     * @param contractId
     * @return
     */
    @ApiOperation("根据合同id查询资质")
    @PostMapping("/listConQualfctnByContractId")
    public BaseResponse listConQualfctnByContractId(@PathVariable Integer contractId) {
        return BaseResponse.successInstance(qualificationMapper.listByContractId(contractId));
    }

    //--------------------------------------------------审核合同------------------------------------------------------

    /**
     * 待审核合同资质分页
     *
     * @param dto
     * @return
     */
    @ApiOperation("待审核合同资质分页")
    @PostMapping("/listContractQuaByCriteria")
    public BaseResponse listContractQuaByCriteria(@RequestBody ListContractQualificationCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return BaseResponse.successInstance(new PageInfo<>(qualificationMapper.listByCriteria(dto)));
    }

    /**
     * 客户合同资质待审核获取下一个
     *
     * @param customerId
     * @return
     */
    @ApiOperation("客户合同资质待审核获取下一个")
    @GetMapping("/getContractByCustmIdAuto/{relevanceId}")
    public BaseResponse getContractByCustmIdAuto(@PathVariable Integer relevanceId) {
        if (this.customerMapper.selectByPrimaryKey(relevanceId) == null) {
            return BaseResponse.errorInstance("客户不存在");
        }
        //查询此客户是否有合同
        List<ReturnContractDto> contractDtoList = this.customerContractMapper.getByCustomerId(relevanceId);
        if (contractDtoList.size() < 1) {
            return BaseResponse.errorInstance("客户未上传合同");
        }
        //返回要审核的客户合同
        List<ReturnCheckContractQualificationDto> contractDtos = this.qualificationMapper.listByCriteria(new ListContractQualificationCriteriaDto());
        if (contractDtos != null && contractDtos.size() > 0) {
            return BaseResponse.successInstance(contractDtos.get(0));
        }
        return BaseResponse.queryDataEmpty();
    }

    /**
     * 审核客户合同资质
     *
     * @param dto
     * @return
     */
    @ApiOperation("审核客户合同资质")
    @PostMapping("/checkContractQualification")
    public BaseResponse listConQualfctnByContractId(@RequestBody UpdateContractQualificationDto dto) {
        if (dto.getId() == null) {
            return BaseResponse.queryDataEmpty();
        }
        //审核记录
        CreateQualificationVerifyLogDto logDto = new CreateQualificationVerifyLogDto();
        if (dto.getCertificateStatus() != 1) {
            if (dto.getDescription() == null) {
                return BaseResponse.errorInstance("审核不通过时描述信息不能为空");
            }
            logDto.setDescription(dto.getDescription());
        }
        this.contractQualificationService.updateContractQuaion(dto);
        logDto
                .setVertifyResult(dto.getCertificateStatus())
                .setReferencesId(dto.getId())
                // 审核类型（1-客户资质 2- 客户合同 3-运力资质 4-运力合同）
                .setCertificateType(2);
        return BaseResponse.service(this.logService.createVerifyLog(logDto));
    }

    @Ignore
    @GetMapping("/getShowCustomerContract/{id}")
    public ShowCustomerContractDto getShowCustomerContractById(@PathVariable("id") Integer id) {
        return customerContractMapper.getShowCustomerContractById(id);
    }
}