package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.contract.CreateContractQualificationDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractQualificationDto;
import com.jaagro.crm.api.service.ContractQualificationService;
import com.jaagro.crm.biz.entity.ContractQualification;
import com.jaagro.crm.biz.mapper.ContractQualificationMapperExt;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createQuation(CreateContractQualificationDto qualificationDto) {
        ContractQualification qualification = new ContractQualification();
        BeanUtils.copyProperties(qualificationDto, qualification);
        qualification.setCreateUserId(this.userService.getCurrentUser().getId());
        this.qualificationMapper.insertSelective(qualification);
        return ServiceResult.toResult("新增成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> updateContractQuaion(UpdateContractQualificationDto qualificationDto) {
        ContractQualification qualification = new ContractQualification();
        BeanUtils.copyProperties(qualificationDto, qualification);
        qualification
                .setModifyUserId(this.userService.getCurrentUser().getId());
        this.qualificationMapper.updateByPrimaryKeySelective(qualification);
        return ServiceResult.toResult("客户资质修改成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> disableContractQuaion(Integer id) {
        ContractQualification qualification = this.qualificationMapper.selectByPrimaryKey(id);
        qualification.setEnabled(false);
        this.qualificationMapper.updateByPrimaryKeySelective(qualification);
        return ServiceResult.toResult("客户资质删除成功");
    }
}
