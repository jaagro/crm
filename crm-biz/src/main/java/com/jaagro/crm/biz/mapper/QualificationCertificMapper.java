package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.QualificationCertific;

public interface QualificationCertificMapper {
    /**
     *
     * @mbggenerated 2018-08-15
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int insert(QualificationCertific record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int insertSelective(QualificationCertific record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    QualificationCertific selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int updateByPrimaryKeySelective(QualificationCertific record);

    /**
     *
     * @mbggenerated 2018-08-15
     */
    int updateByPrimaryKey(QualificationCertific record);
}