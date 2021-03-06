package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.ListContractQualificationCriteriaDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCheckContractQualificationDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractQualificationDto;
import com.jaagro.crm.biz.entity.ContractQualification;
import org.apache.ibatis.annotations.Param;

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
     * 根据合同id和类型查询
     *
     * @param contractId
     * @return
     */
    List<ReturnContractQualificationDto> listQualificationByContractIdAndType(@Param("contractId") Integer contractId, @Param("type") Integer type);

    /**
     * 合同资质证分页
     *
     * @param dto
     * @return
     */
    List<ReturnCheckContractQualificationDto> listByCriteria(ListContractQualificationCriteriaDto dto);

    /**
     * 养殖户合同资质证分页
     *
     * @param dto
     * @return
     */
    List<ReturnCheckContractQualificationDto> listPlantQuaByCriteria(ListContractQualificationCriteriaDto dto);

    /**
     * 根据id查询待审核合同资质详情
     *
     * @param id
     * @return
     */
    ReturnCheckContractQualificationDto getById(Integer id);

    /**
     * 根据id查询合同资质详情
     *
     * @param id
     * @return
     */
    ReturnCheckContractQualificationDto getQualificationById(Integer id);

    /**
     * 根据合同id查询未审核的资质
     *
     * @param contractId
     * @return
     */
    List<ReturnCheckContractQualificationDto> listUnCheckByContractId(Integer contractId);


    /**
     * 根据合同id查询资质类型16、17、18是否都以审核通过
     *
     * @param relevanceId
     * @return
     */
    List<ContractQualification> listCheckedByContract(@Param("relevanceId") Integer relevanceId, @Param("type") Integer type);

    /**
     * 根据养殖合同id查询资质类型80是否都以审核通过
     *
     * @param relevanceId
     * @return
     */
    List<ContractQualification> listYzCheckedByContract(@Param("relevanceId") Integer relevanceId);

    /**
     * 查询某合同的某资质
     *
     * @param relevanceId
     * @return
     */
    List<ContractQualification> getByContractIdAndType(@Param("relevanceId") Integer relevanceId, @Param("relevanceType") Integer relevanceType, @Param("certificateType") Integer certificateType);
}