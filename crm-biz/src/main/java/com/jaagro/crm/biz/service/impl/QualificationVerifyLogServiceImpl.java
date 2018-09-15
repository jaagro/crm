package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.contract.ListContractQualificationCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.CreateQualificationVerifyLogDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCheckContractQualificationDto;
import com.jaagro.crm.api.service.QualificationVerifyLogService;
import com.jaagro.crm.biz.entity.*;
import com.jaagro.crm.biz.mapper.*;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class QualificationVerifyLogServiceImpl implements QualificationVerifyLogService {

    @Autowired
    private CurrentUserService userService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerContractMapper contractMapper;
    @Autowired
    private CustomerQualificationMapper qualificationMapper;
    @Autowired
    private QualificationVerifyLogMapper verifyLogMapper;
    @Autowired
    private TruckTeamContractMapper truckTeamContractMapper;
    @Autowired
    private ContractQualificationMapper contractQualificationMapper;
    @Autowired
    private TruckTeamMapper truckTeamMapper;

    /**
     * 新增审核记录
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createVerifyLog(CreateQualificationVerifyLogDto dto) {
        QualificationVerifyLog verifyLog = new QualificationVerifyLog();
        BeanUtils.copyProperties(dto, verifyLog);
        if (verifyLog.getVertifyResult() != 1) {
            if (StringUtils.isEmpty(verifyLog.getDescription())) {
                throw new RuntimeException("审核不通过时描述信息必填");
            }
        }
        // 审核类型（1-客户资质 2-运力资质）
        switch (verifyLog.getCertificateType()) {
            // 客户资质
            case 1:
                CustomerQualification qualification = this.qualificationMapper.selectByPrimaryKey(verifyLog.getReferencesId());
                if (qualification == null) {
                    throw new RuntimeException("客户资质证不存在");
                }
                Customer customer = this.customerMapper.selectByPrimaryKey(qualification.getCustomerId());
                if (customer == null) {
                    throw new RuntimeException("客户不存在");
                }
                switch (customer.getCustomerType()) {
                    //个体
                    //如身份证正面审核成功 更改客户审核状态为审核通过
                    case 1:
                        if (qualification.getCertificateType() == 7) {
                            if (qualification.getCertificateStatus() == 1) {
                                customer
                                        .setCustomerStatus(AuditStatus.NORMAL_COOPERATION)
                                        .setModifyTime(new Date())
                                        .setModifyUserId(this.userService.getCurrentUser().getId());
                                this.customerMapper.updateByPrimaryKeySelective(customer);
                                break;
                            }
                        }
                        break;
                        //企业
                        //如营业执照审核成功 更改客户审核状态为审核通过
                    default:
                        if (qualification.getCertificateType() == 1) {
                            if (qualification.getCertificateStatus() == 1) {
                                customer
                                        .setCustomerStatus(AuditStatus.NORMAL_COOPERATION)
                                        .setModifyTime(new Date())
                                        .setModifyUserId(this.userService.getCurrentUser().getId());
                                this.customerMapper.updateByPrimaryKeySelective(customer);
                                break;
                            }
                        }
                        break;
                }
                break;
                // 运力资质
            default:
                ContractQualification contractQualification = this.contractQualificationMapper.selectByPrimaryKey(verifyLog.getReferencesId());
                if (contractQualification == null) {
                    throw new RuntimeException("运力合同资质不存在");
                }
                TruckTeamContract teamContract = this.truckTeamContractMapper.selectByPrimaryKey(contractQualification.getRelevanceId());
                if (teamContract == null) {
                    throw new RuntimeException("运力合同不存在");
                }
                //如营业执照审核成功 更改客户审核状态为审核通过
                if (contractQualification.getCertificateType() == 1) {
                    if (contractQualification.getCertificateStatus() == 1) {
                        ListContractQualificationCriteriaDto qualificationCriteriaDto = new ListContractQualificationCriteriaDto();
                        qualificationCriteriaDto
                                .setRelevanceId(teamContract.getId())
                                .setRelevanceType(contractQualification.getRelevanceType());

                        //若车队合同下的资质都已审核完，则修改合同审核状态为审核通过
                        if (this.contractQualificationMapper.listUnCheckByContractId(teamContract.getId()) == null) {
                            teamContract
                                    .setContractStatus(AuditStatus.NORMAL_COOPERATION)
                                    .setModifyTime(new Date())
                                    .setModifyUserId(this.userService.getCurrentUser().getId());
                            this.truckTeamContractMapper.updateByPrimaryKeySelective(teamContract);
                        }
                        //若车队下的合同都审核完，则修改车队状态为审核通过
                        List<ReturnCheckContractQualificationDto> contractDtos = this.contractQualificationMapper.listByCriteria(qualificationCriteriaDto);
                        if (contractDtos.size() > 0) {
                            break;
                        }
                        TruckTeam truckTeam = this.truckTeamMapper.selectByPrimaryKey(teamContract.getTruckTeamId());
                        if (truckTeam != null) {
                            truckTeam
                                    .setTeamStatus(AuditStatus.NORMAL_COOPERATION)
                                    .setModifyTime(new Date())
                                    .setModifyUserId(this.userService.getCurrentUser().getId());
                            this.truckTeamMapper.updateByPrimaryKeySelective(truckTeam);
                        }
                    }
                }
                break;
        }
        verifyLog.setAuditor(this.userService.getCurrentUser().getId());
        this.verifyLogMapper.insertSelective(verifyLog);
        return ServiceResult.toResult("审核成功");
    }
}
