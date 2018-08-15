package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.QualificationCertific;

/**
 * @author baiyiran
 */
public interface QualificationCertificMapper {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(QualificationCertific record);

    /**
     * 动态新增
     *
     * @param record
     * @return
     */
    int insertSelective(QualificationCertific record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    QualificationCertific selectByPrimaryKey(Long id);

    /**
     * 动态更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(QualificationCertific record);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(QualificationCertific record);
}