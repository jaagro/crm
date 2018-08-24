package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.contract.UpdateContractQualificationDto;

import java.util.Map;

/**
 * 资质证件照
 *
 * @author baiyiran
 */
public interface ContractQualificationService {

    /**
     * 修改
     *
     * @param qualificationDto
     * @return
     */
    Map<String, Object> updateContractQuaion(UpdateContractQualificationDto qualificationDto);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableContractQuaion(Integer id);
}
