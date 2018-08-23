package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.QualificationVerifyLog;

public interface QualificationVerifyLogMapper {
    /**
     *
     * @mbggenerated 2018-08-22
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-22
     */
    int insert(QualificationVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-22
     */
    int insertSelective(QualificationVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-22
     */
    QualificationVerifyLog selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-22
     */
    int updateByPrimaryKeySelective(QualificationVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-22
     */
    int updateByPrimaryKey(QualificationVerifyLog record);
}