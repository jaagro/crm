package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.CertificateStatus;
import com.jaagro.crm.api.dto.request.customer.CreateQualificationCertificDto;
import com.jaagro.crm.api.dto.request.customer.ListQualificationCertificDto;
import com.jaagro.crm.api.dto.request.customer.UpdateQualificationCertificDto;
import com.jaagro.crm.api.dto.response.customer.QualificationCertificReturnDto;
import com.jaagro.crm.api.service.QualificationCertificService;
import com.jaagro.crm.biz.entity.QualificationCertific;
import com.jaagro.crm.biz.mapper.QualificationCertificMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.ServiceResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class QualificationCertificServiceImpl implements QualificationCertificService {

    @Autowired
    private QualificationCertificMapper certificMapper;
    @Autowired
    private CurrentUserService userService;

    @Override
    public Map<String, Object> createQualificationCertific(CreateQualificationCertificDto certificDto) {
        QualificationCertific qc = new QualificationCertific();
        BeanUtils.copyProperties(certificDto, qc);
        qc
                .setEnabled(true)
                .setCertificateStatus(CertificateStatus.UNCHECKED)
                .setCreatedUserId(userService.getCurrentUser().getId())
                .setCreatedTime(new Date());
        this.certificMapper.insert(qc);
        return ServiceResult.toResult("资质证件照创建成功");
    }

    @Override
    public Map<String, Object> createQualificationCertific(List<CreateQualificationCertificDto> certificDtos, Long CustomerId) {
        if (certificDtos != null && certificDtos.size() > 0) {
            for (CreateQualificationCertificDto certificDto : certificDtos) {
                QualificationCertific qc = new QualificationCertific();
                BeanUtils.copyProperties(certificDto, qc);
                qc
                        .setEnabled(true)
                        .setCustomerId(CustomerId)
                        .setCertificateStatus(CertificateStatus.UNCHECKED)
                        .setCreatedUserId(userService.getCurrentUser().getId())
                        .setCreatedTime(new Date());
                this.certificMapper.insert(qc);
            }
        }
        return ServiceResult.toResult("资质证件照列表创建成功");
    }

    @Override
    public Map<String, Object> updateQualificationCertific(UpdateQualificationCertificDto certificDto) {
        QualificationCertific qc = new QualificationCertific();
        BeanUtils.copyProperties(certificDto, qc);
        qc
                .setModifyUserId(userService.getCurrentUser().getId())
                .setModifyTime(new Date());
        this.certificMapper.updateByPrimaryKeySelective(qc);
        return ServiceResult.toResult("证件照修改成功");
    }

    @Override
    public Map<String, Object> updateQualificationCertific(List<UpdateQualificationCertificDto> certificDtos) {
        if (certificDtos != null && certificDtos.size() > 0) {
            for (UpdateQualificationCertificDto certificDto : certificDtos) {
                QualificationCertific qc = new QualificationCertific();
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
    public Map<String, Object> getById(Long id) {
        return ServiceResult.toResult(this.certificMapper.selectByPrimaryKey(id));
    }

    @Override
    public Map<String, Object> listByCriteria(ListQualificationCertificDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<QualificationCertificReturnDto> certificReturnDtos = this.certificMapper.getByQualificationCriteriDto(dto);
        return ServiceResult.toResult(new PageInfo<>(certificReturnDtos));
    }

    @Override
    public Map<String, Object> disableQualificationCertific(Long id) {
        QualificationCertific certific = this.certificMapper.selectByPrimaryKey(id);
        certific.setEnabled(false);
        this.certificMapper.updateByPrimaryKeySelective(certific);
        return ServiceResult.toResult("资质证件照删除成功");
    }

    @Override
    public Map<String, Object> disableQualificationCertific(List<QualificationCertificReturnDto> qualifications) {
        if (qualifications != null && qualifications.size() > 0) {
            for (QualificationCertificReturnDto certificReturnDto : qualifications) {
                QualificationCertific certific = this.certificMapper.selectByPrimaryKey(certificReturnDto.getId());
                certific.setEnabled(false);
                this.certificMapper.updateByPrimaryKeySelective(certific);
            }
        }
        return ServiceResult.toResult("资质证件照删除成功");
    }
}
