package com.jaagro.crm.api.dto.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 * @Date 2018/12/26
 */
@Data
@Accessors(chain = true)
public class QueryCustomerContractSettlePriceDto implements Serializable {

    /**
     * 客户合同合同id
     */
    private Integer customerContractId;

    /**
     * 装货地id
     */
    private Integer loadSiteId;

    /**
     * 卸货地id
     */
    private Integer unloadSiteId;

    /**
     * 车辆类型id(0-所有)
     */
    private Integer truckTypeId;

}
