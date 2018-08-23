package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.contract.CreateContractDto;
import com.jaagro.crm.api.dto.request.contract.ListContractCriteriaDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractDto;

import java.util.List;
import java.util.Map;

/**
 * @author tony
 */
public interface ContractService {

    /**
     * 创建合同
     *
     * @param dto
     * @return
     */
    Map<String, Object> createContract(CreateContractDto dto);

    /**
     * 创建合同列表
     *
     * @param dtos
     * @return
     */
    Map<String, Object> createContract(List<CreateContractDto> dtos,Integer CustomerId);

    /**
     * 修改合同
     *
     * @param dto
     * @return
     */
    Map<String, Object> updateContract(UpdateContractDto dto);

    /**
     * 修改合同列表
     *
     * @param dtos
     * @return
     */
    Map<String, Object> updateContract(List<UpdateContractDto> dtos);

    /**
     * 查询单个（含详细子表信息）
     *
     * @param contractId
     * @return
     */
    Map<String, Object> getById(Integer contractId);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    Map<String, Object> listByCriteria(ListContractCriteriaDto dto);

    /**
     * 删除，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableById(Integer id);

    /**
     * 删除，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableByID(List<ReturnContractDto> dtos);

}
