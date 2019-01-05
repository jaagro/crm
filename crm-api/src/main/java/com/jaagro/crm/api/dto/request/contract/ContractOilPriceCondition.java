package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author @Gao.
 */
@Data
@Accessors(chain = true)
public class ContractOilPriceCondition implements Serializable {
    /**
     * 合同id
     */
    private Integer contractId;
}
