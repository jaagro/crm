package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.ListContractQualificationCriteriaDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCheckContractQualificationDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractQualificationDto;
import com.jaagro.crm.biz.entity.ContractQualification;

import java.util.List;

/**
 * @author baiyiran
 */
public interface ContractQualificationMapper {
    /**
     * @mbggenerated 2018-08-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-24
     */
    int insert(ContractQualification record);

    /**
     * @mbggenerated 2018-08-24
     */
    int insertSelective(ContractQualification record);

    /**
     * @mbggenerated 2018-08-24
     */
    ContractQualification selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-24
     */
    int updateByPrimaryKeySelective(ContractQualification record);

    /**
     * @mbggenerated 2018-08-24
     */
    int updateByPrimaryKey(ContractQualification record);

    /**
     * 根据合同id查询dto
     *
     * @param contractId
     * @return
     */
    List<ReturnCheckContractQualificationDto> listByContractId(Integer contractId);

    /**
     * 根据合同id查询
     *
     * @param contractId
     * @return
     */
    List<ReturnContractQualificationDto> listQualificationByContractId(Integer contractId);

    /**
     * 根据合同id查询未审核的资质
     *
     * @param contractId
     * @return
     */
    List<ReturnCheckContractQualificationDto> listUnCheckByContractId(Integer contractId);

    /**
     * 合同资质证分页
     *
     * @param dto
     * @return
     */
    List<ReturnCheckContractQualificationDto> listByCriteria(ListContractQualificationCriteriaDto dto);
}