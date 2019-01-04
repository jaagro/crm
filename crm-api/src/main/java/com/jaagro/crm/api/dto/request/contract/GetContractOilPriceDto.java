package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author @Gao.
 */
@Data
@Accessors(chain = true)
public class GetContractOilPriceDto implements Serializable {
    /**
     * 创建人
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 油价
     */
    private BigDecimal oilPrice;
}
