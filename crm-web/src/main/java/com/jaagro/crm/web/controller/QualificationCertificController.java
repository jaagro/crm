package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerQualificationDto;
import com.jaagro.crm.api.dto.request.customer.CreateQualificationVerifyLogDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerQualificationCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerQualificationDto;
import com.jaagro.crm.api.dto.response.customer.CustomerQualificationReturnDto;
import com.jaagro.crm.api.dto.response.customer.ReturnQualificationDto;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.api.service.OssSignUrlClientService;
import com.jaagro.crm.api.service.QualificationCertificService;
import com.jaagro.crm.api.service.QualificationVerifyLogService;
import com.jaagro.crm.biz.entity.CustomerQualification;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import com.jaagro.crm.biz.mapper.CustomerQualificationMapper;
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


/**
 * 客户资质管理
 *
 * @author baiyiran
 */
@RestController
@Api(value = "qualificationCertific", description = "客户资质证件照管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class QualificationCertificController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private QualificationCertificService certificService;
    @Autowired
    private CustomerQualificationMapper certificMapper;
    @Autowired
    private QualificationVerifyLogService logService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;

    /**
     * 新增资质
     *
     * @param certificDtos
     * @return
     */
    @ApiOperation("新增资质")
    @PostMapping("/qualificationCertific")
    public BaseResponse insertCustomer(@RequestBody List<CreateCustomerQualificationDto> certificDtos) {
        if (certificDtos != null && certificDtos.size() > 0) {
            for (CreateCustomerQualificationDto certificDto : certificDtos) {
                if (certificDto.getCustomerId() == null) {
                    return BaseResponse.idNull("客户id:[customerId]不能为空");
                }
                if (this.customerMapper.selectByPrimaryKey(certificDto.getCustomerId()) == null) {
                    return BaseResponse.errorInstance("客户id:[customerId]不存在");
                }
                if (certificDto.getCertificateType() == null) {
                    return BaseResponse.idNull("证件类型:[certificateType]不能为空");
                }
                if (certificDto.getCertificateImageUrl() == null) {
                    return BaseResponse.idNull("证件图片地址:[certificateImageUrl]不能为空");
                }
                certificService.createQualificationCertific(certificDto);
            }
        }
        return BaseResponse.successInstance("创建成功");
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
            return BaseResponse.errorInstance("查询不到相应数据");
        }
        return BaseResponse.service(this.certificService.disableQualificationCertific(id));
    }

    /**
     * 修改单个资质
     *
     * @param certificDto
     * @return
     */
    @ApiOperation("修改资质")
    @PutMapping("/qualificationCertific")
    public BaseResponse updateSite(@RequestBody List<UpdateCustomerQualificationDto> certificDto) {
        return BaseResponse.service(certificService.updateQualificationCertific(certificDto));
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
        return BaseResponse.service(certificService.deleteQualificationCertific(ids));
    }

    /**
     * 查询单个资质
     *
     * @param id
     * @return
     */
    @ApiOperation("查询单个资质")
    @GetMapping("/qualificationCertific/{id}")
    public BaseResponse getById(@PathVariable Integer id) {
        if (this.certificMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.queryDataEmpty();
        }
        CustomerQualification customerQualification = this.certificMapper.selectByPrimaryKey(id);
        //替换资质证照地址
        String[] strArray = {customerQualification.getCertificateImageUrl()};
        List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
        customerQualification.setCertificateImageUrl(urlList.get(0).toString());
        return BaseResponse.successInstance(customerQualification);
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
        return BaseResponse.service(this.certificService.listByCriteria(criteriaDto));
    }

    /**
     * 根据客户id查询资质列表
     *
     * @param criteriaDto
     * @return
     */
    @ApiOperation("根据客户id查询资质列表")
    @GetMapping("/listQualificationByCustomerId/{customerId}")
    public BaseResponse listByCriteria(@PathVariable("customerId") Integer customerId) {
        if (customerId == null) {
            return BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户id不能为空"));
        }
        return BaseResponse.service(this.certificService.listByCustomerId(customerId));
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
            return BaseResponse.errorInstance("客户不存在");
        }
        //判断客户是否有资质证
        List<CustomerQualificationReturnDto> returnDtos = this.certificMapper.getByCustomerQualificationId(customerId);
        if (returnDtos.size() < 1) {
            return BaseResponse.errorInstance("客户未上传资质证");
        }
        ListCustomerQualificationCriteriaDto dto = new ListCustomerQualificationCriteriaDto();
        dto.setCustomerId(customerId);
        //返回要审核的资质信息
        List<ReturnQualificationDto> qualificationDtos = this.certificMapper.listByCustomerIdAndStatus(dto);
        if (qualificationDtos != null && qualificationDtos.size() > 0) {
            return BaseResponse.successInstance(qualificationDtos.get(0));
        }
        return BaseResponse.queryDataEmpty();
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
            return BaseResponse.errorInstance("资质证件照不存在");
        }
        //审核记录
        CreateQualificationVerifyLogDto logDto = new CreateQualificationVerifyLogDto();
        if (dto.getCertificateStatus() != 1) {
            if (dto.getDescription() == null) {
                return BaseResponse.errorInstance("审核不通过时描述信息不能为空");
            }
            logDto.setDescription(dto.getDescription());
        }
        this.certificService.updateQualificationCertific(dto);
        logDto
                .setVertifyResult(dto.getCertificateStatus())
                .setReferencesId(dto.getId())
                .setCertificateType(1);
        return BaseResponse.service(this.logService.createVerifyLog(logDto));
    }
}
