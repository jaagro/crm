package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.customer.CreateQualificationVerifyLogDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerQualificationDto;
import com.jaagro.crm.api.dto.request.truck.CreateListTruckQualificationDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckQualificationCriteriaDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckQualificationDto;
import com.jaagro.crm.api.dto.response.truck.DriverReturnDto;
import com.jaagro.crm.api.dto.response.truck.ReturnTruckQualificationDto;
import com.jaagro.crm.api.service.DriverClientService;
import com.jaagro.crm.api.service.QualificationCertificService;
import com.jaagro.crm.api.service.QualificationVerifyLogService;
import com.jaagro.crm.api.service.TruckQualificationService;
import com.jaagro.crm.biz.mapper.TruckMapper;
import com.jaagro.crm.biz.mapper.TruckQualificationMapper;
import com.jaagro.crm.biz.mapper.TruckTeamMapper;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liqiangping
 */
@RestController
@Api(value = "truckQualification", description = "车队资质管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class TruckQualificationController {

    @Autowired
    private TruckQualificationService truckQualificationService;
    @Autowired
    private TruckQualificationMapper truckQualificationMapper;
    @Autowired
    private TruckTeamMapper truckTeamMapper;
    @Autowired
    private TruckMapper truckMapper;
    @Autowired
    private QualificationCertificService certificService;
    @Autowired
    private QualificationVerifyLogService logService;
    @Autowired
    private DriverClientService driverClientService;

    @ApiOperation("新增资质")
    @PostMapping("/truckQualification")
    public BaseResponse insert(@RequestBody CreateListTruckQualificationDto dto) {
        if (StringUtils.isEmpty(truckTeamMapper.selectByPrimaryKey(dto.getTruckTeamId()))) {
            return BaseResponse.errorInstance(dto.getTruckTeamId() + " :车队不存在");
        }
        if (StringUtils.isEmpty(dto.getQualification())) {
            return BaseResponse.errorInstance("请上传资质");
        }
        return BaseResponse.service(truckQualificationService.createTruckQualification(dto));
    }

    //-----------------------------------------------------审核-------------------------------------------------------

    @ApiOperation("待审核资质分页")
    @PostMapping("/listQualification")
    public BaseResponse listQualification(@RequestBody ListTruckQualificationCriteriaDto criteriaDto) {
        return BaseResponse.service(truckQualificationService.listQualification(criteriaDto));
    }

    @ApiOperation("待审核资质下一条")
    @GetMapping("/getQualificationAuto")
    public BaseResponse listQualification(@PathVariable Integer teamId) {
        if (this.truckTeamMapper.selectByPrimaryKey(teamId) == null) {
            BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "当前车队不存在"));
        }
        //返回待审核资质下一条
        List<ReturnTruckQualificationDto> qualificationDtos = this.truckQualificationMapper.listByTeamId(teamId);
        if (qualificationDtos != null && qualificationDtos.size() > 0) {
            ReturnTruckQualificationDto qualificationDto = qualificationDtos.get(0);
            //填充司机信息
            if (qualificationDto.getDriverId() != null) {
                qualificationDto.setDriverReturnDto(this.driverClientService.getDriverReturnObject(qualificationDto.getDriverId()));
            }
            //填充车辆信息
            if (qualificationDto.getTruckId() != null) {
                qualificationDto.setTruckDto(this.truckMapper.getCheckById(qualificationDto.getTruckId()));
            }
            return BaseResponse.successInstance(qualificationDto);
        }
        return BaseResponse.queryDataEmpty();
    }

    @ApiOperation("审核资质")
    @PostMapping("/checkTruckQualification")
    public BaseResponse checkTruckQualification(@RequestBody UpdateTruckQualificationDto criteriaDto) {
        if (StringUtils.isEmpty(criteriaDto.getId())) {
            BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质id不能为空"));
        }
        if (this.truckQualificationMapper.selectByPrimaryKey(criteriaDto.getId()) == null) {
            BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "当前资质不存在"));
        }
        if (StringUtils.isEmpty(criteriaDto.getCertificateType())) {
            BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质证件照类型不能为空"));
        }
        if (StringUtils.isEmpty(criteriaDto.getCertificateStatus())) {
            BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "审核结果不能为空"));
        }
        //审核记录
        CreateQualificationVerifyLogDto logDto = new CreateQualificationVerifyLogDto();
        UpdateCustomerQualificationDto dto = new UpdateCustomerQualificationDto();
        BeanUtils.copyProperties(criteriaDto, dto);
        this.certificService.updateQualificationCertific(dto);
        if (!criteriaDto.getCertificateStatus().equals(AuditStatus.AUDIT_FAILED)) {
            if (StringUtils.isEmpty(criteriaDto.getDescription())) {
                BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "审核不通过时需填写描述信息"));
            }
            logDto.setDescription(dto.getDescription());
        }
        logDto
                .setVertifyResult(dto.getCertificateStatus())
                .setReferencesId(dto.getId())
                .setCertificateType(3);
        return BaseResponse.service(this.logService.createVerifyLog(logDto));
    }


}
