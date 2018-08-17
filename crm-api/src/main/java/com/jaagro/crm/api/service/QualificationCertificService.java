package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateQualificationCertificDto;
import com.jaagro.crm.api.dto.request.customer.ListQualificationCertificDto;
import com.jaagro.crm.api.dto.request.customer.UpdateQualificationCertificDto;
import com.jaagro.crm.api.dto.response.customer.QualificationCertificReturnDto;

import java.util.List;
import java.util.Map;

/**
 * dto请参照customer
 *
 * @author baiyiran
 */
public interface QualificationCertificService {

    /**
     * 新增单个资质证件照，注意新增CreateDto
     *
     * @param qualificationCertificDto
     * @return
     */
    Map<String, Object> createQualificationCertific(CreateQualificationCertificDto qualificationCertificDto);

    /**
     * 新增资质证件照列表
     *
     * @param qualificationCertificDtos
     * @return
     */
    Map<String, Object> createQualificationCertific(List<CreateQualificationCertificDto> qualificationCertificDtos, Long CustomerId);

    /**
     * 修改单个资质证件照，注意新增updateDto
     *
     * @return
     */
    Map<String, Object> updateQualificationCertific(UpdateQualificationCertificDto qualificationCertificDto);

    /**
     * 修改资质证件照列表
     *
     * @param qualificationCertificDtos
     * @return
     */
    Map<String, Object> updateQualificationCertific(List<UpdateQualificationCertificDto> qualificationCertificDtos);

    /**
     * 获取单条记录
     *
     * @param id
     * @return
     */
    Map<String, Object> getById(Long id);

    /**
     * 根据条件分页获取
     *
     * @param dto
     * @return
     */
    Map<String, Object> listByCriteria(ListQualificationCertificDto dto);

    /**
     * 删除资质证件照，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableQualificationCertific(Long id);

    /**
     * 删除资质证件照，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableQualificationCertific(List<QualificationCertificReturnDto> qualifications);
}
