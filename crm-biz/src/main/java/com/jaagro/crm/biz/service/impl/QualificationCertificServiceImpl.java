package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerQualificationDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerQualificationCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerQualificationDto;
import com.jaagro.crm.api.dto.response.customer.CustomerQualificationReturnDto;
import com.jaagro.crm.api.dto.response.customer.ReturnQualificationDto;
import com.jaagro.crm.api.service.OssSignUrlClientService;
import com.jaagro.crm.api.service.QualificationCertificService;
import com.jaagro.crm.biz.entity.CustomerQualification;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import com.jaagro.crm.biz.mapper.CustomerQualificationMapper;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URL;
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
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Map<String, Object> createQualificationCertific(CreateCustomerQualificationDto certificDto) {
        CustomerQualification qc = new CustomerQualification();
        BeanUtils.copyProperties(certificDto, qc);
        qc
                .setCreateUserId(userService.getCurrentUser().getId());
        this.certificMapper.insertSelective(qc);
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

    /**
     * 这里只能审核客户资质，车队资质审核在哪里？？？
     */

    @Override
    public Map<String, Object> updateQualificationCertific(UpdateCustomerQualificationDto certificDto) {
        CustomerQualification qc = new CustomerQualification();
        BeanUtils.copyProperties(certificDto, qc);
        qc
                .setModifyUserId(userService.getCurrentUser().getId())
                .setModifyTime(new Date());
        this.certificMapper.updateByPrimaryKeySelective(qc);
        return ServiceResult.toResult("证件照修改成功");
    }

    @Override
    public Map<String, Object> updateQualificationCertific(List<UpdateCustomerQualificationDto> certificDtos) {
        if (certificDtos != null && certificDtos.size() > 0) {
            for (UpdateCustomerQualificationDto certificDto : certificDtos) {
                if (certificDto.getId() == null) {
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "证件id不能为空");
                }
                if (this.certificMapper.selectByPrimaryKey(certificDto.getId()) == null) {
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "证件[id=" + certificDto.getId() + "]不存在");
                }
                //若url为空，则先删除
                if (StringUtils.isEmpty(certificDto.getCertificateImageUrl())) {
                    this.certificMapper.deleteByPrimaryKey(certificDto.getId());
                    CustomerQualification qc = new CustomerQualification();
                    BeanUtils.copyProperties(certificDto, qc);
                    qc.setCreateUserId(userService.getCurrentUser().getId());
                    this.certificMapper.insertSelective(qc);
                }
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
    public Map<String, Object> getDetailById(Integer id) {
        ReturnQualificationDto returnQualificationDto = this.certificMapper.getDetailById(id);
        //替换资质证照地址
        String[] strArray = {returnQualificationDto.getCertificateImageUrl()};
        List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
        returnQualificationDto.setCertificateImageUrl(urlList.get(0).toString());
        return ServiceResult.toResult(returnQualificationDto);
    }

    @Override
    public Map<String, Object> listByCriteria(ListCustomerQualificationCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<ReturnQualificationDto> certificReturnDtos = this.certificMapper.listByCustomerIdAndStatus(dto);
        if (certificReturnDtos.size() > 0) {
            for (ReturnQualificationDto qualificationReturnDto : certificReturnDtos) {
                //替换资质证照地址
                String[] strArray = {qualificationReturnDto.getCertificateImageUrl()};
                List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
                qualificationReturnDto.setCertificateImageUrl(urlList.get(0).toString());
            }
        }
        return ServiceResult.toResult(new PageInfo<>(certificReturnDtos));
    }

    @Override
    public Map<String, Object> listByCustomerId(Integer customerId) {
        if (this.customerMapper.selectByPrimaryKey(customerId) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "此客户不存在");
        }
        ListCustomerQualificationCriteriaDto dto = new ListCustomerQualificationCriteriaDto();
        dto.setCustomerId(customerId);
        List<ReturnQualificationDto> certificReturnDtos = this.certificMapper.listByCustomerIdAndStatus(dto);
        if (certificReturnDtos != null && certificReturnDtos.size() > 0) {
            for (ReturnQualificationDto qualificationReturnDto : certificReturnDtos) {
                //替换资质证照地址
                String[] strArray = {qualificationReturnDto.getCertificateImageUrl()};
                List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
                qualificationReturnDto.setCertificateImageUrl(urlList.get(0).toString());
            }
        }
        return ServiceResult.toResult(certificReturnDtos);
    }

    @Override
    public Map<String, Object> deleteQualificationCertific(List<Integer> ids) {
        if (ids != null && ids.size() > 0) {
            for (Integer id : ids) {
                this.certificMapper.deleteByPrimaryKey(id);
            }
        }
        return ServiceResult.toResult("删除成功");
    }

    @Override
    public Map<String, Object> disableQualificationCertific(Integer id) {
        CustomerQualification certific = this.certificMapper.selectByPrimaryKey(id);
        certific.setEnabled(false);
        this.certificMapper.updateByPrimaryKeySelective(certific);
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
