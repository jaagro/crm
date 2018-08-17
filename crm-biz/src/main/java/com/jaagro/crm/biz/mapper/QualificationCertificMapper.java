package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListQualificationCertificDto;
import com.jaagro.crm.api.dto.response.customer.QualificationCertificReturnDto;
import com.jaagro.crm.biz.entity.QualificationCertific;

import java.util.List;

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

    /**
     * 查询客户Id查询资质证明
     *
     * @param id
     * @return
     */
    List<QualificationCertificReturnDto> getByCustomerQualificationId(Long id);

    /**
     * 分页查询
     *
     * @param certificDto
     * @return
     */
    List<QualificationCertificReturnDto> getByQualificationCriteriDto(ListQualificationCertificDto certificDto);

}