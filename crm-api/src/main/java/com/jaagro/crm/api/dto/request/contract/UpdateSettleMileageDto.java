package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author baiyiran
 * @Date 2018/12/29
 */
@Data
@Accessors(chain = true)
public class UpdateSettleMileageDto implements Serializable {

    private static final long serialVersionUID = -8172479595478794743L;
    /**
     * 结算里程表id
     */
    private Integer id;

    /**
     * 司机结算里程
     */
    private BigDecimal driverSettleMileage;

}
