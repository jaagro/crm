package com.jaagro.crm.api.service;

import java.util.Map;

/**
 * @author baiyiran
 */
public interface ContractPriceService {

    /**
     * 删除，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableByContractId(Long contractId);

}
