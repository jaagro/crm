package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerQualificationDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerQualificationCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerQualificationDto;
import com.jaagro.crm.api.dto.response.customer.CustomerQualificationReturnDto;

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
    Map<String, Object> createQualificationCertific(CreateCustomerQualificationDto qualificationCertificDto);

    /**
     * 新增资质证件照列表
     *
     * @param qualificationCertificDtos
     * @return
     */
    Map<String, Object> createQualificationCertific(List<CreateCustomerQualificationDto> qualificationCertificDtos, Integer CustomerId);

    /**
     * 修改单个资质证件照，注意新增updateDto
     *
     * @return
     */
    Map<String, Object> updateQualificationCertific(UpdateCustomerQualificationDto qualificationCertificDto);

    /**
     * 修改资质证件照列表
     *
     * @param qualificationCertificDtos
     * @return
     */
    Map<String, Object> updateQualificationCertific(List<UpdateCustomerQualificationDto> qualificationCertificDtos);

    /**
     * 获取单条记录
     *
     * @param id
     * @return
     */
    Map<String, Object> getById(Integer id);

    /**
     * 获取审核资质详情
     *
     * @param id
     * @return
     */
    Map<String, Object> getDetailById(Integer id);

    /**
     * 根据条件分页获取
     *
     * @param dto
     * @return
     */
    Map<String, Object> listByCriteria(ListCustomerQualificationCriteriaDto dto);

    /**
     * 删除资质证件照
     *
     * @param id
     * @return
     */
    Map<String, Object> deleteQualificationCertific(List<Integer> ids);

    /**
     * 逻辑删除资质证件照
     *
     * @param id
     * @return
     */
    Map<String, Object> disableQualificationCertific(Integer id);

    /**
     * 逻辑删除资质证件照
     *
     * @param id
     * @return
     */
    Map<String, Object> disableQualificationCertific(List<CustomerQualificationReturnDto> qualifications);
}
