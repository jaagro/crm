package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.CertificateType;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerQualificationDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerQualificationCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerQualificationDto;
import com.jaagro.crm.api.dto.response.customer.CustomerQualificationReturnDto;
import com.jaagro.crm.api.dto.response.customer.ReturnQualificationDto;
import com.jaagro.crm.api.service.QualificationCertificService;
import com.jaagro.crm.biz.entity.CustomerQualification;
import com.jaagro.crm.biz.mapper.CustomerMapperExt;
import com.jaagro.crm.biz.mapper.CustomerQualificationMapperExt;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private CustomerQualificationMapperExt certificMapper;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private CustomerMapperExt customerMapper;

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createQualificationCertific(CreateCustomerQualificationDto certificDto) {
        if (this.customerMapper.selectByPrimaryKey(certificDto.getCustomerId()) == null) {
            throw new RuntimeException("客户不存在");
        }
        CustomerQualification qc = new CustomerQualification();
        BeanUtils.copyProperties(certificDto, qc);
        if (StringUtils.isEmpty(qc.getCertificateType())) {
            throw new RuntimeException("资质证件照类型不能为空");
        }
        if (!certificDto.getCertificateType().equals(CertificateType.ELSE)) {
            //新增前判断是否此资质已新增过
            List<CustomerQualificationReturnDto> returnDtos = certificMapper.getByCustomerIdAndId(qc.getCustomerId(), qc.getCertificateType());
            if (returnDtos.size() > 0) {
                throw new RuntimeException("此客户的资质已上传，不允许再上传");
            }
        }
        qc
                .setCreateUserId(userService.getCurrentUser().getId());
        try {
            this.certificMapper.insertSelective(qc);
            //新增后的返回：替换资质证照地址
            String[] strArray = {qc.getCertificateImageUrl()};
            List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
            qc.setCertificateImageUrl(urlList.get(0).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
        return ServiceResult.toResult(qc);
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
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
    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> updateQualificationCertific(UpdateCustomerQualificationDto certificDto) {
        //如果id为空，则新增此条数据
        if (certificDto.getId() == null) {
            CreateCustomerQualificationDto createCustomerQualificationDto = new CreateCustomerQualificationDto();
            BeanUtils.copyProperties(certificDto, createCustomerQualificationDto);
            try {
                this.createQualificationCertific(createCustomerQualificationDto);
            } catch (Exception ex) {
                ex.printStackTrace();
                return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), ex.getMessage());
            }
        } else {
            //修改
            CustomerQualification qualification = this.certificMapper.selectByPrimaryKey(certificDto.getId());
            if (qualification == null) {
                return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "证件" + certificDto.getId() + "不存在");
            }
            CustomerQualification qc = new CustomerQualification();
            BeanUtils.copyProperties(certificDto, qc);
            qc
                    .setModifyUserId(userService.getCurrentUser().getId())
                    .setModifyTime(new Date());
            /**
             * 修改前判断是否已审核过
             */
            // 待审核
            if (qualification.getCertificateStatus().equals(AuditStatus.UNCHECKED)) {
                this.certificMapper.updateByPrimaryKeySelective(qc);
            }
            // 审核未通过的
            if (qualification.getCertificateStatus().equals(AuditStatus.AUDIT_FAILED)) {
                // 先将审核未通过的资质逻辑删除
                qualification
                        .setEnabled(false)
                        .setCertificateStatus(AuditStatus.STOP_COOPERATION);
                this.certificMapper.updateByPrimaryKeySelective(qualification);
                // 把新资质证件照新增
                qc
                        .setId(null)
                        .setCertificateStatus(AuditStatus.UNCHECKED)
                        .setCreateUserId(userService.getCurrentUser().getId())
                        .setCustomerId(qualification.getCustomerId());
                this.certificMapper.insertSelective(qc);
            }
        }
        //修改后的返回：替换资质证照地址
        String[] strArray = {certificDto.getCertificateImageUrl()};
        List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
        certificDto.setCertificateImageUrl(urlList.get(0).toString());
        return ServiceResult.toResult(certificDto);
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> updateQualificationCertific(List<UpdateCustomerQualificationDto> certificDtos) {
        if (certificDtos != null && certificDtos.size() > 0) {
            for (UpdateCustomerQualificationDto certificDto : certificDtos) {
                //如果id为空，则新增此条数据
                if (certificDto.getId() == null) {
                    CreateCustomerQualificationDto createCustomerQualificationDto = new CreateCustomerQualificationDto();
                    BeanUtils.copyProperties(certificDto, createCustomerQualificationDto);
                    try {
                        this.createQualificationCertific(createCustomerQualificationDto);
                    } catch (Exception ex) {
                        return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), ex.getMessage());
                    }
                } else {
                    //修改
                    CustomerQualification qualification = this.certificMapper.selectByPrimaryKey(certificDto.getId());
                    if (qualification == null) {
                        return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "证件[id=" + certificDto.getId() + "]不存在");
                    }
                    CustomerQualification qc = new CustomerQualification();
                    BeanUtils.copyProperties(certificDto, qc);
                    qc
                            .setModifyUserId(userService.getCurrentUser().getId())
                            .setModifyTime(new Date());
                    /**
                     * 修改前判断是否已审核过
                     */
                    // 待审核
                    if (qualification.getCertificateStatus().equals(AuditStatus.UNCHECKED)) {
                        this.certificMapper.updateByPrimaryKeySelective(qc);
                    }
                    // 审核未通过的
                    if (qualification.getCertificateStatus().equals(AuditStatus.AUDIT_FAILED)) {
                        // 先将审核未通过的资质逻辑删除
                        qualification
                                .setEnabled(false)
                                .setCertificateStatus(AuditStatus.STOP_COOPERATION);
                        this.certificMapper.updateByPrimaryKeySelective(qualification);
                        // 把新资质证件照新增
                        qc
                                .setId(null)
                                .setCertificateStatus(AuditStatus.UNCHECKED)
                                .setCreateUserId(userService.getCurrentUser().getId())
                                .setCustomerId(qualification.getCustomerId());
                        this.certificMapper.insertSelective(qc);
                    }
                }
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
        dto
                .setCustomerId(customerId)
                .setEnableCheck("查询详情");
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

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> deleteQualificationCertific(List<Integer> ids) {
        if (ids != null && ids.size() > 0) {
            for (Integer id : ids) {
                this.certificMapper.deleteByPrimaryKey(id);
            }
        }
        return ServiceResult.toResult("删除成功");
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> disableQualificationCertific(Integer id) {
        CustomerQualification certific = this.certificMapper.selectByPrimaryKey(id);
        certific.setEnabled(false);
        this.certificMapper.updateByPrimaryKeySelective(certific);
        return ServiceResult.toResult("资质证件照删除成功");
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
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

    @Override
    public ReturnQualificationDto getQualificationById(Integer id) {
        return this.certificMapper.getQualificationById(id);
    }

}
