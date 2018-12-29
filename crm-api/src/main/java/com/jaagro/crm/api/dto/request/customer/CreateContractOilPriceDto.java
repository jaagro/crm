package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
@Accessors(chain = true)
@Data
public class CreateContractOilPriceDto implements Serializable {

    /**
     * 合同id
     */
    private Integer contractId;

    /**
     * 合同类型,1-客户合同,2-司机合同
     */
    private Integer contractType;

    /**
     * 价格(元/升)
     */
    private BigDecimal price;

    /**
     * 生效时间
     */
    private Date effectiveTime;

    /**
     * 失效时间
     */
    private Date invalidTime;
}
