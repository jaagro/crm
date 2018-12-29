package com.jaagro.crm.api.dto.request.contract;

import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 结算配制新增dto
 *
 * @author baiyiran
 * @Date 2018/12/28
 */
@Data
@Accessors(chain = true)
public class CreateContractSettleDto implements Serializable {
    private static final long serialVersionUID = 2368344814835416448L;
    private Integer contractId;

    private CreateContractOilPriceDto oilPriceDto;

    private CreateCustomerSettleRuleDto ruleDto;
}
