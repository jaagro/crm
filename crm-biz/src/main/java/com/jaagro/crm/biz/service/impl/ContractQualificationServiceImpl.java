package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.contract.UpdateContractQualificationDto;
import com.jaagro.crm.api.service.ContractQualificationService;
import com.jaagro.crm.biz.entity.ContractQualification;
import com.jaagro.crm.biz.mapper.ContractQualificationMapper;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class ContractQualificationServiceImpl implements ContractQualificationService {

    private static final Logger log = LoggerFactory.getLogger(ContractQualificationServiceImpl.class);

    @Autowired
    private ContractQualificationMapper qualificationMapper;
    @Autowired
    private CurrentUserService userService;

    @Override
    public Map<String, Object> updateContractQuaion(UpdateContractQualificationDto qualificationDto) {
        ContractQualification qualification = new ContractQualification();
        BeanUtils.copyProperties(qualificationDto, qualification);
        qualification
                .setModifyUserId(this.userService.getCurrentUser().getId());
        this.qualificationMapper.updateByPrimaryKeySelective(qualification);
        log.info("【客户资质修改】------成功\nContractQualification:" + qualification.toString());
        return ServiceResult.toResult("客户资质修改成功");
    }

    @Override
    public Map<String, Object> disableContractQuaion(Integer id) {
        ContractQualification qualification = this.qualificationMapper.selectByPrimaryKey(id);
        qualification.setEnabled(false);
        this.qualificationMapper.updateByPrimaryKeySelective(qualification);
        log.info("【客户资质删除】------成功\nContractQualification:" + id);
        return ServiceResult.toResult("客户资质删除成功");
    }
}
