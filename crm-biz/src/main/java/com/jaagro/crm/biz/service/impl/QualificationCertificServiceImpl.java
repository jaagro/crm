package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.CertificateStatus;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerQualificationDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerQualificationCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerQualificationDto;
import com.jaagro.crm.api.dto.response.customer.CustomerQualificationReturnDto;
import com.jaagro.crm.api.service.QualificationCertificService;
import com.jaagro.crm.biz.entity.CustomerQualification;
import com.jaagro.crm.biz.mapper.CustomerQualificationMapper;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class QualificationCertificServiceImpl implements QualificationCertificService {

    private static final Logger log = LoggerFactory.getLogger(QualificationCertificServiceImpl.class);

    @Autowired
    private CustomerQualificationMapper certificMapper;
    @Autowired
    private CurrentUserService userService;

    @Override
    public Map<String, Object> createQualificationCertific(CreateCustomerQualificationDto certificDto) {
        CustomerQualification qc = new CustomerQualification();
        BeanUtils.copyProperties(certificDto, qc);
        qc
                .setCreateUserId(userService.getCurrentUser().getId());
        this.certificMapper.insertSelective(qc);
        log.info("【资质证照新增】-------成功\nCustomerQualification:" + qc.toString());
        return ServiceResult.toResult("资质证件照创建成功");
    }

    @Override
    public Map<String, Object> createQualificationCertific(List<CreateCustomerQualificationDto> certificDtos, Integer CustomerId) {
        if (certificDtos != null && certificDtos.size() > 0) {
            for (CreateCustomerQualificationDto certificDto : certificDtos) {
                CustomerQualification qc = new CustomerQualification();
                BeanUtils.copyProperties(certificDto, qc);
                qc
                        .setCreateUserId(userService.getCurrentUser().getId());
                this.certificMapper.insertSelective(qc);
            }
        }
        return ServiceResult.toResult("资质证件照列表创建成功");
    }

    @Override
    public Map<String, Object> updateQualificationCertific(UpdateCustomerQualificationDto certificDto) {
        CustomerQualification qc = new CustomerQualification();
        BeanUtils.copyProperties(certificDto, qc);
        qc
                .setModifyUserId(userService.getCurrentUser().getId())
                .setModifyTime(new Date());
        this.certificMapper.updateByPrimaryKeySelective(qc);
        log.info("【资质证照修改】-------成功\nCustomerQualification:" + qc.toString());
        return ServiceResult.toResult("证件照修改成功");
    }

    @Override
    public Map<String, Object> updateQualificationCertific(List<UpdateCustomerQualificationDto> certificDtos) {
        if (certificDtos != null && certificDtos.size() > 0) {
            for (UpdateCustomerQualificationDto certificDto : certificDtos) {
                CustomerQualification qc = new CustomerQualification();
                BeanUtils.copyProperties(certificDto, qc);
                qc
                        .setModifyUserId(userService.getCurrentUser().getId())
                        .setModifyTime(new Date());
                this.certificMapper.updateByPrimaryKeySelective(qc);
            }
        }
        return ServiceResult.toResult("证件照列表修改成功");
    }

    @Override
    public Map<String, Object> getById(Integer id) {
        return ServiceResult.toResult(this.certificMapper.selectByPrimaryKey(id));
    }

    @Override
    public Map<String, Object> listByCriteria(ListCustomerQualificationCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<CustomerQualificationReturnDto> certificReturnDtos = this.certificMapper.getByQualificationCriteriDto(dto);
        return ServiceResult.toResult(new PageInfo<>(certificReturnDtos));
    }

    @Override
    public Map<String, Object> disableQualificationCertific(Integer id) {
        CustomerQualification certific = this.certificMapper.selectByPrimaryKey(id);
        certific.setEnabled(false);
        this.certificMapper.updateByPrimaryKeySelective(certific);
        log.info("【资质证照删除】-------成功\nCustomerQualification:" + id);
        return ServiceResult.toResult("资质证件照删除成功");
    }

    @Override
    public Map<String, Object> disableQualificationCertific(List<CustomerQualificationReturnDto> qualifications) {
        if (qualifications != null && qualifications.size() > 0) {
            for (CustomerQualificationReturnDto certificReturnDto : qualifications) {
                CustomerQualification certific = this.certificMapper.selectByPrimaryKey(certificReturnDto.getId());
                certific.setEnabled(false);
                this.certificMapper.updateByPrimaryKeySelective(certific);
            }
        }
        return ServiceResult.toResult("资质证件照删除成功");
    }
}
