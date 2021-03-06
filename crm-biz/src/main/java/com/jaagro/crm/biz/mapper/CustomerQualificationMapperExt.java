package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListCustomerQualificationCriteriaDto;
import com.jaagro.crm.api.dto.response.customer.CustomerQualificationReturnDto;
import com.jaagro.crm.api.dto.response.customer.ReturnQualificationDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gavin
 */
public interface CustomerQualificationMapperExt extends CustomerQualificationMapper {

    /**
     * 查询客户Id查询资质证明
     *
     * @param id
     * @return
     */
    List<CustomerQualificationReturnDto> getByCustomerQualificationId(Integer id);

    /**
     * 分页查询
     *
     * @param certificDto
     * @return
     */
    List<CustomerQualificationReturnDto> getByQualificationCriteriDto(ListCustomerQualificationCriteriaDto certificDto);

    /**
     * 根据客户查询待审核的
     *
     * @param certificDto
     * @return
     */

    List<ReturnQualificationDto> listByCustomerIdAndStatus(ListCustomerQualificationCriteriaDto certificDto);

    /**
     * 查询客户除审核成功的个数
     *
     * @param customerId
     * @return
     */
    int countByCustomerIdAndStatus(Integer customerId);

    /**
     * 获取待审核资质详情
     *
     * @param id
     * @return
     */
    ReturnQualificationDto getDetailById(Integer id);

    /**
     * 查询某客户的某资质是否已存在 (除审核不通过外)
     *
     * @param customerId
     * @param qualificationId
     * @return
     */
    List<CustomerQualificationReturnDto> getByCustomerIdAndId(@Param("customerId") Integer customerId, @Param("certificateType") Integer certificateType);

    /**
     * 查询单个资质（包括审核页面右侧信息）
     *
     * @return
     */
    ReturnQualificationDto getQualificationById(Integer id);
}