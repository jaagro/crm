package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.QualificationVerifyLog;

public interface QualificationVerifyLogMapper {
    /**
     *
     * @mbggenerated 2018-08-29
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-29
     */
    int insert(QualificationVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-29
     */
    int insertSelective(QualificationVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-29
     */
    QualificationVerifyLog selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-29
     */
    int updateByPrimaryKeySelective(QualificationVerifyLog record);

    /**
     *
     * @mbggenerated 2018-08-29
     */
    int updateByPrimaryKey(QualificationVerifyLog record);
}