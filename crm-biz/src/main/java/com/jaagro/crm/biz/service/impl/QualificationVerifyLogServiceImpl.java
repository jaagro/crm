package com.jaagro.crm.biz.service.impl;

import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.AccountType;
import com.jaagro.crm.api.constant.AccountUserType;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.CertificateType;
import com.jaagro.crm.api.dto.request.customer.CreateQualificationVerifyLogDto;
import com.jaagro.crm.api.dto.response.truck.DriverReturnDto;
import com.jaagro.crm.api.service.AccountService;
import com.jaagro.crm.api.service.QualificationVerifyLogService;
import com.jaagro.crm.biz.entity.*;
import com.jaagro.crm.biz.mapper.*;
import com.jaagro.crm.biz.service.DriverClientService;
import com.jaagro.utils.ResponseStatusCode;
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
    private CustomerMapperExt customerMapper;
    @Autowired
    private CustomerContractMapperExt contractMapper;
    @Autowired
    private CustomerQualificationMapperExt qualificationMapper;
    @Autowired
    private QualificationVerifyLogMapperExt verifyLogMapper;
    @Autowired
    private ContractQualificationMapperExt contractQualificationMapper;
    @Autowired
    private TruckTeamMapperExt truckTeamMapper;
    @Autowired
    private TruckTeamContractMapperExt truckTeamContractMapper;
    @Autowired
    private TruckQualificationMapperExt truckQualificationMapper;
    @Autowired
    private DriverClientService driverClientService;
    @Autowired
    private TruckMapperExt truckMapper;
    @Autowired
    private AccountService accountService;
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
        if (verifyLog.getVertifyResult().equals(AuditStatus.AUDIT_FAILED)) {
            if (StringUtils.isEmpty(verifyLog.getDescription())) {
                throw new RuntimeException("审核不通过时描述信息必填");
            }
        }
        // 审核类型
        // 1-客户资质 2-运力资质 3-客户合同 4-运力合同
        switch (verifyLog.getCertificateType()) {
            /**
             * 客户资质
             */
            case 1:
                CustomerQualification customerQualification = this.qualificationMapper.selectByPrimaryKey(verifyLog.getReferencesId());
                if (customerQualification == null) {
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户资质证不存在");
                }
                Customer customer = this.customerMapper.selectByPrimaryKey(customerQualification.getCustomerId());
                if (customer == null) {
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户不存在");
                }
                switch (customer.getCustomerType()) {
                    //个体
                    //如身份证正面审核成功 更改客户审核状态为审核通过
                    case 1:
                        if (customerQualification.getCertificateType() == 7) {
                            if (customerQualification.getCertificateStatus() == 1) {
                                UserInfo currentUser = this.userService.getCurrentUser();
                                Integer currentUserId = currentUser == null ? null : currentUser.getId();
                                customer
                                        .setCustomerStatus(AuditStatus.NORMAL_COOPERATION)
                                        .setModifyTime(new Date())
                                        .setModifyUserId(currentUserId);
                                this.customerMapper.updateByPrimaryKeySelective(customer);
                                // 创建账户 add by yj 20181025
                                accountService.createAccount(customer.getId(), AccountUserType.CUSTOMER, AccountType.CASH,currentUserId);
                                break;
                            }
                        }
                        break;
                    //企业
                    //如营业执照审核成功 更改客户审核状态为审核通过
                    default:
                        if (customerQualification.getCertificateType() == 1) {
                            if (customerQualification.getCertificateStatus() == 1) {
                                UserInfo currentUser = this.userService.getCurrentUser();
                                Integer currentUserId = currentUser == null ? null : currentUser.getId();
                                customer
                                        .setCustomerStatus(AuditStatus.NORMAL_COOPERATION)
                                        .setModifyTime(new Date())
                                        .setModifyUserId(this.userService.getCurrentUser().getId());
                                this.customerMapper.updateByPrimaryKeySelective(customer);
                                // 创建账户 add by yj 20181025
                                accountService.createAccount(customer.getId(),AccountUserType.CUSTOMER,AccountType.CASH,currentUserId);
                                break;
                            }
                        }
                        break;
                }
                break;

            /**
             * 运力资质
             */
            case 2:
                TruckQualification truckQualification = truckQualificationMapper.selectByPrimaryKey(dto.getReferencesId());
                if (truckQualification == null) {
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车队资质证不存在");
                }
                UserInfo currentUser = userService.getCurrentUser();
                Integer currentUserId = currentUser == null ? null : currentUser.getId();
                //司机资质审核
                if (!StringUtils.isEmpty(truckQualification.getDriverId())) {
                    DriverReturnDto driverReturnDto = driverClientService.getDriverReturnObject(truckQualification.getDriverId());
                    if (driverReturnDto == null) {
                        return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "司机不存在");
                    }
                    if (truckQualification.getCertificateType().equals(CertificateType.FRONT_ID_CARD) ||
                            truckQualification.getCertificateType().equals(CertificateType.BACK_ID_CARD) ||
                            truckQualification.getCertificateType().equals(CertificateType.DRIVER_ORIGINAL) ||
                            truckQualification.getCertificateType().equals(CertificateType.TRANSPORT_QUALIFICATION) ||
                            truckQualification.getCertificateType().equals(CertificateType.DRIVER_COPY)) {

                        if (truckQualification.getCertificateStatus().equals(AuditStatus.NORMAL_COOPERATION)) {
                            // type为： 1:个体车队 2:公司车队 3:车辆 4:司机
                            int result = truckQualificationMapper.listCheckedByIdAndType(truckQualification.getDriverId(), 4);
                            if (result == 5) {
                                //若司机7、8、9、10、11身份证正面、身份证反面、驾驶证正面、驾驶证反面、道路运输从业资格证 审核均通过，则修改司机为审核通过
                                driverClientService.updateDriverStatusFeign(driverReturnDto.getId());
                                // 创建账户 add by yj 20181025
                                accountService.createAccount(driverReturnDto.getId(),AccountUserType.DRIVER,AccountType.CASH,currentUserId);
                                break;
                            }
                            break;
                        }
                    }
                }
                //车辆资质审核
                if (!StringUtils.isEmpty(truckQualification.getTruckId()) && StringUtils.isEmpty(truckQualification.getDriverId())) {
                    Truck truck = truckMapper.selectByPrimaryKey(truckQualification.getTruckId());
                    if (truck == null) {
                        return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车辆不存在");
                    }
                    if (truckQualification.getCertificateType().equals(CertificateType.DRIVING_LICENSE_ORIGINAL) || truckQualification.getCertificateType().equals(CertificateType.DRIVING_LICENSE_COPY) || truckQualification.getCertificateType().equals(CertificateType.OPERATION_LICENSE) || truckQualification.getCertificateType().equals(CertificateType.COMPULSORY_INSURANCE) || truckQualification.getCertificateType().equals(CertificateType.BUSINESS_INSURANCE)) {
                        if (truckQualification.getCertificateStatus().equals(AuditStatus.NORMAL_COOPERATION)) {
                            // type为： 1:个体车队 2:公司车队 3:车辆 4:司机
                            int result = truckQualificationMapper.listCheckedByIdAndType(truckQualification.getTruckId(), 3);
                            if (result == 5) {
                                //若车辆2、3、4、5、6行驶证正本、行驶证副本、营运证、保险单强险、保险单商业险 审核均通过，则修改司机为审核通过
                                truck
                                        .setTruckStatus(AuditStatus.NORMAL_COOPERATION)
                                        .setModifyTime(new Date())
                                        .setModifyUserId(userService.getCurrentUser().getId());
                                truckMapper.updateByPrimaryKeySelective(truck);
                                break;
                            }
                            break;
                        }
                    }
                }
                //车队资质审核
                if (!StringUtils.isEmpty(truckQualification.getTruckTeamId()) && StringUtils.isEmpty(truckQualification.getTruckId()) && StringUtils.isEmpty(truckQualification.getDriverId())) {
                    TruckTeam truckTeam = truckTeamMapper.selectByPrimaryKey(truckQualification.getTruckTeamId());
                    if (truckTeam == null) {
                        return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车队不存在");
                    }
                    switch (truckTeam.getTeamType()) {
                        //个体
                        case 1:
                            if (truckQualification.getCertificateStatus().equals(AuditStatus.NORMAL_COOPERATION)) {
                                if (truckQualification.getCertificateType().equals(CertificateType.FRONT_ID_CARD) || truckQualification.getCertificateType().equals(CertificateType.BACK_ID_CARD) || truckQualification.getCertificateType().equals(CertificateType.GROUP_PHOTO)) {
                                    // type为： 1:个体车队 2:公司车队 3:车辆 4:司机
                                    int result = truckQualificationMapper.listCheckedByIdAndType(truckQualification.getTruckTeamId(), 1);
                                    //若个体车队 7、8、12 身份证正面、身份证反面、人车合影图片审核通过，则修改车队状态为审核通过
                                    if (result == 3) {
                                        truckTeam
                                                .setTeamStatus(AuditStatus.NORMAL_COOPERATION)
                                                .setModifyTime(new Date())
                                                .setModifyUserId(userService.getCurrentUser().getId());
                                        truckTeamMapper.updateByPrimaryKeySelective(truckTeam);
                                        break;
                                    }
                                    break;
                                }
                            }
                            break;
                        //车队
                        default:
                            if (truckQualification.getCertificateStatus().equals(AuditStatus.NORMAL_COOPERATION)) {
                                if (truckQualification.getCertificateType().equals(CertificateType.BUSINESS_LICENSE) || truckQualification.getCertificateType().equals(CertificateType.TRANSPORT_ROUTIER)) {
                                    // type为： 1:个体车队 2:公司车队 3:车辆 4:司机
                                    int result = truckQualificationMapper.listCheckedByIdAndType(truckQualification.getTruckTeamId(), 2);
                                    //若公司车队 1、19 营业执照、道路运输许可证审核通过，则修改车队状态为审核通过
                                    if (result == 2) {
                                        truckTeam
                                                .setTeamStatus(AuditStatus.NORMAL_COOPERATION)
                                                .setModifyTime(new Date())
                                                .setModifyUserId(userService.getCurrentUser().getId());
                                        truckTeamMapper.updateByPrimaryKeySelective(truckTeam);
                                        break;
                                    }
                                    break;
                                }
                            }
                            break;
                    }
                    break;
                }
                break;
            /**
             * 客户合同
             */
            case 3:
                ContractQualification qualification = contractQualificationMapper.selectByPrimaryKey(dto.getReferencesId());
                if (qualification == null) {
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户合同资质不存在");
                }
                CustomerContract contract = this.contractMapper.selectByPrimaryKey(qualification.getRelevanceId());
                if (contract == null) {
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户合同不存在");
                }
                //判断 客户合同资质类型16、17、18 是否都以审核通过
                if (qualification.getCertificateStatus().equals(AuditStatus.NORMAL_COOPERATION)) {
                    if (qualification.getCertificateType().equals(CertificateType.CONTTRACT_INDEX) || qualification.getCertificateType().equals(CertificateType.CONTTRACT_SCEAU) || qualification.getCertificateType().equals(CertificateType.CONTTRACT_PRICE)) {
                        List<ContractQualification> contractQualifications = contractQualificationMapper.listCheckedByContract(qualification.getRelevanceId(), 1);
                        if (contractQualifications.size() >= 3) {
                            //合同资质均已审核通过，则修改合同状态为审核通过
                            contract
                                    .setContractStatus(AuditStatus.NORMAL_COOPERATION)
                                    .setNewUpdateUser(userService.getCurrentUser().getId())
                                    .setNewUpdateTime(new Date());
                            contractMapper.updateByPrimaryKeySelective(contract);
                            break;
                        }
                    }
                }
                break;
            /**
             * 运力合同
             */
            case 4:
                ContractQualification contractQualification = contractQualificationMapper.selectByPrimaryKey(dto.getReferencesId());
                if (contractQualification == null) {
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "运力合同资质不存在");
                }
                TruckTeamContract truckContract = this.truckTeamContractMapper.selectByPrimaryKey(contractQualification.getRelevanceId());
                if (truckContract == null) {
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "运力合同不存在");
                }
                //判断 运力合同资质类型16、17、18 是否都以审核通过
                if (contractQualification.getCertificateStatus().equals(AuditStatus.NORMAL_COOPERATION)) {
                    if (contractQualification.getCertificateType().equals(CertificateType.CONTTRACT_INDEX) || contractQualification.getCertificateType().equals(CertificateType.CONTTRACT_SCEAU) || contractQualification.getCertificateType().equals(CertificateType.CONTTRACT_PRICE)) {
                        List<ContractQualification> contractQualifications = contractQualificationMapper.listCheckedByContract(contractQualification.getRelevanceId(), 2);
                        if (contractQualifications.size() == 3) {
                            //合同资质均已审核通过，则修改合同状态为审核通过
                            truckContract
                                    .setContractStatus(AuditStatus.NORMAL_COOPERATION)
                                    .setModifyUserId(userService.getCurrentUser().getId())
                                    .setModifyTime(new Date());
                            truckTeamContractMapper.updateByPrimaryKeySelective(truckContract);
                            break;
                        }
                    }
                }
                break;

            default:
                break;
        }
        verifyLog.setAuditor(this.userService.getCurrentUser().getId());
        this.verifyLogMapper.insertSelective(verifyLog);
        return ServiceResult.toResult("审核成功");
    }
}
