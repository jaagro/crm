package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.contract.CreateContractQualificationDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractQualificationDto;
import com.jaagro.crm.api.service.ContractQualificationService;
import com.jaagro.crm.biz.entity.ContractQualification;
import com.jaagro.crm.biz.mapper.ContractQualificationMapperExt;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class ContractQualificationServiceImpl implements ContractQualificationService {

    private static final Logger log = LoggerFactory.getLogger(ContractQualificationServiceImpl.class);

    @Autowired
    private ContractQualificationMapperExt qualificationMapper;
    @Autowired
    private CurrentUserService userService;

    /**
     * 新增
     *
     * @param qualificationDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createQuation(CreateContractQualificationDto qualificationDto) {
        ContractQualification qualification = new ContractQualification();
        BeanUtils.copyProperties(qualificationDto, qualification);
        if (StringUtils.isEmpty(qualification.getRelevanceId()) || StringUtils.isEmpty(qualification.getRelevanceType()) || StringUtils.isEmpty(qualification.getCertificateType())) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同资质缺少参数");
        }
        List<ContractQualification> qualificationList = qualificationMapper.getByContractIdAndType(qualification.getRelevanceId(), qualification.getRelevanceType(), qualification.getCertificateType());
        if (qualificationList.size() > 0) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "此合同已上传资质，不允许再上传");
        }
        qualification.setCreateUserId(this.userService.getCurrentUser().getId());
        this.qualificationMapper.insertSelective(qualification);
        return ServiceResult.toResult("新增成功");
    }

    /**
     * 修改合同资质
     *
     * @param qualificationDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> updateContractQuaion(UpdateContractQualificationDto qualificationDto) {
        if (qualificationDto.getId() == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "证件id不能为空");
        }
        ContractQualification qualification = this.qualificationMapper.selectByPrimaryKey(qualificationDto.getId());
        if (qualification == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "证件[id=" + qualification.getId() + "]不存在");
        }
        ContractQualification contractQualification = new ContractQualification();
        BeanUtils.copyProperties(qualificationDto, contractQualification);
        contractQualification.setModifyUserId(this.userService.getCurrentUser().getId());
        /**
         * 修改前判断是否已审核过
         */
        // 已审核通过
        if (qualification.getCertificateStatus().equals(AuditStatus.NORMAL_COOPERATION)) {
            return ServiceResult.error("已审核通过的证件照不允许再修改");
        }
        // 待审核
        if (qualification.getCertificateStatus().equals(AuditStatus.UNCHECKED)) {
            this.qualificationMapper.updateByPrimaryKeySelective(contractQualification);
            return ServiceResult.toResult("操作成功");
        }
        // 审核未通过的
        if (qualificationDto.getCertificateStatus().equals(AuditStatus.AUDIT_FAILED)) {
            // 先将审核未通过的资质逻辑删除
            qualification
                    .setEnabled(false)
                    .setCertificateStatus(AuditStatus.STOP_COOPERATION);
            this.qualificationMapper.updateByPrimaryKeySelective(qualification);
            // 把新资质证件照新增
            this.qualificationMapper.insertSelective(contractQualification);
            return ServiceResult.toResult("操作成功");
        }
        // 已删除的
        if (qualification.getCertificateStatus().equals(AuditStatus.STOP_COOPERATION) || qualification.getEnabled().equals(0)) {
            return ServiceResult.error("证件照已被删除");
        }
        return ServiceResult.error("操作失败");
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> disableContractQuaion(Integer id) {
        ContractQualification qualification = this.qualificationMapper.selectByPrimaryKey(id);
        qualification.setEnabled(false);
        this.qualificationMapper.updateByPrimaryKeySelective(qualification);
        return ServiceResult.toResult("客户资质删除成功");
    }
}
