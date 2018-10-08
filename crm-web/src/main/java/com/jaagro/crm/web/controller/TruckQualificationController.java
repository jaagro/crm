package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.customer.CreateQualificationVerifyLogDto;
import com.jaagro.crm.api.dto.request.truck.CreateListTruckQualificationDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckQualificationCriteriaDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckQualificationDto;
import com.jaagro.crm.api.dto.response.truck.ReturnTruckQualificationDto;
import com.jaagro.crm.api.service.*;
import com.jaagro.crm.biz.mapper.TruckMapperExt;
import com.jaagro.crm.biz.mapper.TruckQualificationMapperExt;
import com.jaagro.crm.biz.mapper.TruckTeamMapperExt;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
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
    private TruckQualificationMapperExt truckQualificationMapper;
    @Autowired
    private TruckTeamMapperExt truckTeamMapper;
    @Autowired
    private TruckMapperExt truckMapper;
    @Autowired
    private QualificationVerifyLogService qualificationVerifyLogService;
    @Autowired
    private TruckVerifyLogService logService;
    @Autowired
    private DriverClientService driverClientService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;

    @ApiOperation("新增资质")
    @PostMapping("/truckQualification")
    public BaseResponse insert(@RequestBody CreateListTruckQualificationDto dto) {
        if (StringUtils.isEmpty(truckTeamMapper.selectByPrimaryKey(dto.getTruckTeamId()))) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车队不存在");
        }
        if (StringUtils.isEmpty(dto.getQualification())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "请上传资质");
        }
        try {
            truckQualificationService.createTruckQualification(dto);
        } catch (Exception ex) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), ex.getMessage());
        }
        return BaseResponse.successInstance("操作成功");
    }

    @ApiOperation("修改资质")
    @PutMapping("/truckQualification")
    public BaseResponse update(@RequestBody List<UpdateTruckQualificationDto> dto) {
        try {
            truckQualificationService.updateQualification(dto);
        } catch (Exception ex) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), ex.getMessage());
        }
        return BaseResponse.successInstance("操作成功");
    }

    @ApiOperation("删除资质")
    @PostMapping("/deleteTruckQualification")
    public BaseResponse update(@RequestBody Integer[] ids) {
        return BaseResponse.service(truckQualificationService.deleteQualification(ids));
    }

    /**
     * 根据三种id查询资质列表
     *
     * @param criteriaDto
     * @return
     */
    @ApiOperation("根据三种id查询资质列表")
    @PostMapping("/listQualificationByTeamId")
    public BaseResponse listQualificationByTeamId(@RequestBody ListTruckQualificationCriteriaDto criteriaDto) {
        criteriaDto.setEnableCheck("查询详情");
        return BaseResponse.service(truckQualificationService.listQualificationByTruckIds(criteriaDto));
    }

    //-----------------------------------------------------审核-------------------------------------------------------

    @ApiOperation("待审核资质分页")
    @PostMapping("/listQualification")
    public BaseResponse listQualification(@RequestBody ListTruckQualificationCriteriaDto criteriaDto) {
        return BaseResponse.service(truckQualificationService.listQualification(criteriaDto));
    }

    @ApiOperation("待审核资质下一条")
    @GetMapping("/getQualificationAuto/{truckTeamId}")
    public BaseResponse getQualificationAuto(@PathVariable Integer truckTeamId) {
        if (this.truckTeamMapper.selectByPrimaryKey(truckTeamId) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "当前车队不存在");
        }
        //返回待审核资质下一条
        ListTruckQualificationCriteriaDto criteriaDto = new ListTruckQualificationCriteriaDto();
        criteriaDto.setTruckTeamId(truckTeamId);
        List<ReturnTruckQualificationDto> qualificationDtos = this.truckQualificationMapper.listByIds(criteriaDto);
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
            //替换资质证照地址
            String[] strArray = {qualificationDto.getCertificateImageUrl()};
            List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
            qualificationDto.setCertificateImageUrl(urlList.get(0).toString());
            return BaseResponse.successInstance(qualificationDto);
        }
        return BaseResponse.successInstance(null);
    }

    @ApiOperation("待审核资质详情")
    @GetMapping("/getQualificationById/{id}")
    public BaseResponse getQualificationById(@PathVariable Integer id) {
        if (this.truckQualificationMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质证不存在");
        }
        ReturnTruckQualificationDto qualificationDto = this.truckQualificationMapper.getById(id);
        if (qualificationDto != null) {
            //填充司机信息
            if (qualificationDto.getDriverId() != null) {
                qualificationDto.setDriverReturnDto(this.driverClientService.getDriverReturnObject(qualificationDto.getDriverId()));
            }
            //填充车辆信息
            if (qualificationDto.getTruckId() != null) {
                qualificationDto.setTruckDto(this.truckMapper.getCheckById(qualificationDto.getTruckId()));
            }
            //替换资质证照地址
            String[] strArray = {qualificationDto.getCertificateImageUrl()};
            List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
            qualificationDto.setCertificateImageUrl(urlList.get(0).toString());
            return BaseResponse.successInstance(qualificationDto);
        }
        return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "无数据");
    }

    @ApiOperation("审核资质")
    @PostMapping("/checkTruckQualification")
    public BaseResponse checkTruckQualification(@RequestBody UpdateTruckQualificationDto criteriaDto) {
        if (StringUtils.isEmpty(criteriaDto.getId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质id不能为空");
        }
        if (this.truckQualificationMapper.selectByPrimaryKey(criteriaDto.getId()) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "当前资质不存在");
        }
        if (StringUtils.isEmpty(criteriaDto.getCertificateType())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质证件照类型不能为空");
        }
        if (StringUtils.isEmpty(criteriaDto.getCertificateStatus())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "审核结果不能为空");
        }
        //审核记录
        CreateQualificationVerifyLogDto logDto = new CreateQualificationVerifyLogDto();
        UpdateTruckQualificationDto dto = new UpdateTruckQualificationDto();
        BeanUtils.copyProperties(criteriaDto, dto);
        this.truckQualificationService.updateQualificationCertific(dto);
        if (!criteriaDto.getCertificateStatus().equals(AuditStatus.NORMAL_COOPERATION)) {
            if (StringUtils.isEmpty(criteriaDto.getDescription())) {
                return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "审核不通过时需填写描述信息");
            }
            logDto.setDescription(dto.getDescription());
        }
        logDto
                .setReferencesId(dto.getId())
                .setCertificateType(2);
        // 1-客户资质 2-运力资质 3-客户合同 4-运力合同
        return BaseResponse.service(this.qualificationVerifyLogService.createVerifyLog(logDto));
    }


}
