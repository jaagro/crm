package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.CreateContractDto;
import com.jaagro.crm.api.dto.request.ContractCriteriaDto;

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
     * 修改合同
     *
     * @param dto
     * @return
     */
    Map<String, Object> updateContract(CreateContractDto dto);

    /**
     * 查询单个（含详细子表信息）
     *
     * @param contractId
     * @return
     */
    Map<String, Object> getByPrimaryKey(Long contractId);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    Map<String, Object> listByPage(ContractCriteriaDto dto);

}
