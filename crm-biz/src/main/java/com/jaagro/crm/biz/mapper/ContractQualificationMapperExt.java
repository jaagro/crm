package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.ListContractQualificationCriteriaDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCheckContractQualificationDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractQualificationDto;
import com.jaagro.crm.biz.entity.ContractQualification;

import java.util.List;

/**
 * @author gavin
 */
public interface ContractQualificationMapperExt extends ContractQualificationMapper {

    /**
     * 根据合同id查询
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
     * 合同资质证分页
     *
     * @param dto
     * @return
     */
    List<ReturnCheckContractQualificationDto> listByCriteria(ListContractQualificationCriteriaDto dto);

    /**
     * 根据id查询待审核合同资质详情
     *
     * @param id
     * @return
     */
    ReturnCheckContractQualificationDto getById(Integer id);

    /**
     * 根据合同id查询未审核的资质
     *
     * @param contractId
     * @return
     */
    List<ReturnCheckContractQualificationDto> listUnCheckByContractId(Integer contractId);


    /**
     * 根据合同id查询资质类型6、17、18是否都以审核通过
     *
     * @param relevanceId
     * @return
     */
    List<ContractQualification> listCheckedByContract(Integer relevanceId,Integer type);
}