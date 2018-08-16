package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.customer.CustomerSiteReturnDto;
import com.jaagro.crm.api.dto.response.customer.QualificationCertificReturnDto;
import com.jaagro.crm.biz.entity.QualificationCertific;

import java.util.List;

public interface QualificationCertificMapper {
    /**
     *
     * @mbggenerated 2018-08-16
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int insert(QualificationCertific record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int insertSelective(QualificationCertific record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    QualificationCertific selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKeySelective(QualificationCertific record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKey(QualificationCertific record);

    /**
     * 查询客户Id查询资质证明
     *
     * @param dto
     * @return
     */
    List<QualificationCertificReturnDto> getByCustomerQualificationId(Long id);
}