package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerQualificationDto;
import com.jaagro.crm.api.dto.request.customer.CreateQualificationVerifyLogDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerQualificationCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerQualificationDto;
import com.jaagro.crm.api.dto.response.customer.CustomerQualificationReturnDto;
import com.jaagro.crm.api.dto.response.customer.ReturnQualificationDto;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.crm.api.service.QualificationCertificService;
import com.jaagro.crm.api.service.QualificationVerifyLogService;
import com.jaagro.crm.biz.entity.CustomerQualification;
import com.jaagro.crm.biz.mapper.CustomerMapperExt;
import com.jaagro.crm.biz.mapper.CustomerQualificationMapperExt;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;
import java.util.Map;


/**
 * 客户资质管理
 *
 * @author baiyiran
 */
@RestController
@Api(value = "qualificationCertific", description = "客户资质证件照管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class QualificationCertificController {

    @Autowired
    private QualificationVerifyLogService logService;
    @Autowired
    private QualificationCertificService qualificationCertificService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private CustomerMapperExt customerMapper;
    @Autowired
    private CustomerQualificationMapperExt certificMapper;

    /**
     * 新增资质【单张】
     *
     * @param customerQualificationDto
     * @return
     */
    @ApiOperation("新增资质【单张】")
    @PostMapping("/qualificationCertificByOne")
    public BaseResponse insertQualificationCertificByOne(@RequestBody CreateCustomerQualificationDto customerQualificationDto) {
        if (customerQualificationDto.getCustomerId() == null) {
            return BaseResponse.idNull("客户id不能为空");
        }
        if (customerQualificationDto.getCertificateType() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "证件类型不能为空");
        }
        if (customerQualificationDto.getCertificateImageUrl() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "证件图片地址不能为空");
        }
        Map<String, Object> result;
        try {
            result = qualificationCertificService.createQualificationCertific(customerQualificationDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return BaseResponse.errorInstance(ex.getMessage());
        }
        return BaseResponse.successInstance(result);
    }

    /**
     * 修改资质【单个】
     *
     * @param dto
     * @return
     */
    @ApiOperation("修改资质【单个】")
    @PutMapping("/qualificationCertificByOne")
    public BaseResponse updateQualificationCertificByOne(@RequestBody UpdateCustomerQualificationDto dto) {
        if (dto.getId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质id不能为空");
        }
        Map<String, Object> result;
        try {
            result = qualificationCertificService.updateQualificationCertific(dto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return BaseResponse.errorInstance(ex.getMessage());
        }
        return BaseResponse.successInstance(result);
    }

    /**
     * 新增资质
     *
     * @param qualificationDtoList
     * @return
     */
    @ApiOperation("新增资质")
    @PostMapping("/qualificationCertific")
    public BaseResponse insertQualificationCertific(@RequestBody List<CreateCustomerQualificationDto> qualificationDtoList) {
        if (qualificationDtoList != null && qualificationDtoList.size() > 0) {
            for (CreateCustomerQualificationDto dto : qualificationDtoList) {
                if (dto.getCustomerId() == null) {
                    return BaseResponse.idNull("客户不能为空");
                }
                if (this.customerMapper.selectByPrimaryKey(dto.getCustomerId()) == null) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户id不存在");
                }
                if (dto.getCertificateType() == null) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "证件类型不能为空");
                }
                if (dto.getCertificateImageUrl() == null) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "证件图片地址不能为空");
                }
                try {
                    qualificationCertificService.createQualificationCertific(dto);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return BaseResponse.errorInstance(ex.getMessage());
                }
            }
            return BaseResponse.successInstance("上传成功");
        }
        return BaseResponse.errorInstance("创建失败");
    }

    /**
     * 删除资质[逻辑]
     *
     * @param id
     * @return
     */
    @ApiOperation("删除资质[逻辑]")
    @DeleteMapping("/deleteQualificationCertificById/{id}")
    public BaseResponse deleteById(@PathVariable Integer id) {
        if (this.certificMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到相应数据");
        }
        return BaseResponse.service(this.qualificationCertificService.disableQualificationCertific(id));
    }

    /**
     * 修改资质
     *
     * @param qualificationDtoList
     * @return
     */
    @ApiOperation("修改资质")
    @PutMapping("/qualificationCertific")
    public BaseResponse updateQualificationCertific(@RequestBody List<UpdateCustomerQualificationDto> qualificationDtoList) {
        try {
            qualificationCertificService.updateQualificationCertific(qualificationDtoList);
        } catch (Exception ex) {
            return BaseResponse.successInstance(ex.getMessage());
        }
        return BaseResponse.successInstance("证件照列表修改成功");
    }

    /**
     * 删除资质
     *
     * @param ids
     * @return
     */
    @ApiOperation("删除资质")
    @PostMapping("/deleteQualificationCertific")
    public BaseResponse deleteQualificationCertific(@RequestBody List<Integer> ids) {
        return BaseResponse.service(qualificationCertificService.deleteQualificationCertific(ids));
    }

    /**
     * 查询单个资质
     *
     * @param id
     * @return
     */
    @ApiOperation("查询单个资质【包含详细客户信息】")
    @GetMapping("/qualificationCertific/{id}")
    public BaseResponse getById(@PathVariable Integer id) {
        if (this.certificMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.queryDataEmpty();
        }
        ReturnQualificationDto qualificationDto = this.qualificationCertificService.getQualificationById(id);
        if (qualificationDto != null) {
            //替换资质证照地址
            String[] strArray = {qualificationDto.getCertificateImageUrl()};
            List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
            qualificationDto.setCertificateImageUrl(urlList.get(0).toString());
        }
        return BaseResponse.successInstance(qualificationDto);
    }

    /**
     * 分页查询资质
     *
     * @param criteriaDto
     * @return
     */
    @ApiOperation("分页查询资质")
    @PostMapping("/listQualificationCertificByCriteria")
    public BaseResponse listByCriteria(@RequestBody ListCustomerQualificationCriteriaDto criteriaDto) {
        return BaseResponse.service(this.qualificationCertificService.listByCriteria(criteriaDto));
    }

    /**
     * 根据客户id查询资质列表
     *
     * @param criteriaDto
     * @return
     */
    @ApiOperation("根据客户id查询资质列表")
    @GetMapping("/ /{customerId}")
    public BaseResponse listByCriteria(@PathVariable("customerId") Integer customerId) {
        if (customerId == null) {
            return BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户id不能为空"));
        }
        return BaseResponse.service(this.qualificationCertificService.listByCustomerId(customerId));
    }

    /**
     * 待审核资质下一个
     *
     * @param customerId
     * @return
     */
    @ApiOperation("待审核资质下一个")
    @GetMapping("/getQalfcationByCustmIdAuto/{customerId}")
    @ApiImplicitParam(name = "customerId", value = "客户id", required = true, dataType = "Integer", paramType = "path")
    public BaseResponse getQalfcationByCustmIdAuto(@PathVariable Integer customerId) {
        if (this.customerMapper.selectByPrimaryKey(customerId) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户不存在");
        }
        //判断客户是否有资质证
        List<CustomerQualificationReturnDto> returnDtos = this.certificMapper.getByCustomerQualificationId(customerId);
        if (returnDtos.size() < 1) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户未上传资质证");
        }
        ListCustomerQualificationCriteriaDto dto = new ListCustomerQualificationCriteriaDto();
        dto.setCustomerId(customerId);
        //返回要审核的资质信息
        List<ReturnQualificationDto> qualificationDtos = this.certificMapper.listByCustomerIdAndStatus(dto);
        if (qualificationDtos != null && qualificationDtos.size() > 0) {
            String[] strArray = {qualificationDtos.get(0).getCertificateImageUrl()};
            List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
            qualificationDtos.get(0).setCertificateImageUrl(urlList.get(0).toString());
            return BaseResponse.successInstance(qualificationDtos.get(0));
        }
        return BaseResponse.successInstance(null);
    }

    /**
     * 待审核资质详情
     *
     * @param customerId
     * @return
     */
    @ApiOperation("待审核资质详情")
    @GetMapping("/getQalfcationById/{id}")
    @ApiImplicitParam(name = "id", value = "资质证id", required = true, dataType = "Integer", paramType = "path")
    public BaseResponse getQalfcationById(@PathVariable Integer id) {
        CustomerQualification qualification = this.certificMapper.selectByPrimaryKey(id);
        if (qualification == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质不存在");
        }
        return BaseResponse.service(qualificationCertificService.getDetailById(id));
    }

    /**
     * 审核资质
     *
     * @param dto
     * @return
     */
    @ApiOperation("审核资质")
    @PostMapping("/checkQualification")
    public BaseResponse checkQualification(@RequestBody UpdateCustomerQualificationDto dto) {
        if (this.certificMapper.selectByPrimaryKey(dto.getId()) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质证件照不存在");
        }
        //审核记录
        CreateQualificationVerifyLogDto logDto = new CreateQualificationVerifyLogDto();
        if (!dto.getCertificateStatus().equals(AuditStatus.NORMAL_COOPERATION)) {
            if (dto.getDescription() == null) {
                return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "审核不通过时描述信息不能为空");
            }
            //审核日志的不通过原因描述
            logDto.setDescription(dto.getDescription());
        }
        dto.setDescription(null);
        this.qualificationCertificService.updateQualificationCertific(dto);
        logDto
                .setReferencesId(dto.getId())
                .setVertifyResult(dto.getCertificateStatus())
                .setCertificateType(1);
        // 1-客户资质 2-运力资质 3-客户合同 4-运力合同
        return BaseResponse.service(this.logService.createVerifyLog(logDto));
    }
}
