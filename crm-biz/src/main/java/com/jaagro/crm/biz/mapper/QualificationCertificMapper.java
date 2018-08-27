package com.jaagro.crm.biz.mapper;


import com.jaagro.crm.biz.entity.QualificationCertific;

public interface QualificationCertificMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(QualificationCertific record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(QualificationCertific record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    QualificationCertific selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(QualificationCertific record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(QualificationCertific record);
}