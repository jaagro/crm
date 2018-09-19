package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.customer.CreateQualificationVerifyLogDto;
import com.jaagro.crm.api.service.QualificationVerifyLogService;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.entity.CustomerQualification;
import com.jaagro.crm.biz.entity.QualificationVerifyLog;
import com.jaagro.crm.biz.mapper.CustomerContractMapperExt;
import com.jaagro.crm.biz.mapper.CustomerMapperExt;
import com.jaagro.crm.biz.mapper.CustomerQualificationMapperExt;
import com.jaagro.crm.biz.mapper.QualificationVerifyLogMapperExt;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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

    /**
     * 新增审核记录
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> createVerifyLog(CreateQualificationVerifyLogDto dto) {
        QualificationVerifyLog verifyLog = new QualificationVerifyLog();
        BeanUtils.copyProperties(dto, verifyLog);
        if (verifyLog.getVertifyResult() != 1) {
            if (StringUtils.isEmpty(verifyLog.getDescription())) {
                throw new RuntimeException("审核不通过时描述信息必填");
            }
        }
        // 审核类型（1-客户资质 2- 客户合同 3-运力资质 4-运力合同）
        switch (verifyLog.getCertificateType()) {
            // 客户资质
            case 1:
                CustomerQualification qualification = this.qualificationMapper.selectByPrimaryKey(verifyLog.getReferencesId());
                if (qualification == null) {
                    throw new RuntimeException("客户资质证不存在");
                }
                //如营业执照审核成功 更改客户审核状态为审核通过
                if (qualification.getCertificateType() == 1) {
                    if (qualification.getCertificateStatus() == 1) {
                        Customer customer = this.customerMapper.selectByPrimaryKey(qualification.getCustomerId());
                        if (customer == null) {
                            throw new RuntimeException("客户不存在");
                        }
                        customer
                                .setCustomerStatus(1)
                                .setModifyTime(new Date())
                                .setModifyUserId(this.userService.getCurrentUser().getId());
                        this.customerMapper.updateByPrimaryKeySelective(customer);
                    }
                }
                break;
            // 客户合同
            case 2:
                if (this.contractMapper.selectByPrimaryKey(verifyLog.getReferencesId()) == null) {
                    throw new RuntimeException("客户合同不存在");
                }
                break;
            // 运力资质  待完善
            case 3:
                break;
            // 运力合同  待完善
            default:
                break;
        }
        verifyLog.setAuditor(this.userService.getCurrentUser().getId());
        this.verifyLogMapper.insertSelective(verifyLog);
        return ServiceResult.toResult("审核成功");
    }
}
