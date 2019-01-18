package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author baiyiran
 * @Date 2018/12/27
 */
@Data
@Accessors(chain = true)
public class CreateSettleMileageDto implements Serializable {
    private static final long serialVersionUID = 6897014485949379057L;
    /**
     * 客户合同id
     */
    private Integer customerContractId;

    /**
     * 部门id
     */
    private Integer departmentId;

    /**
     * 项目部名称
     */
    private String departmentName;

    /**
     * 装货地id
     */
    private Integer loadSiteId;

    /**
     * 装货地名称
     */
    private String loadSiteName;

    /**
     *
     */
    private Integer unloadSiteId;

    /**
     * 卸货地名称
     */
    private String unloadSiteName;

    /**
     * 客户结算里程
     */
    private BigDecimal customerSettleMileage;

    /**
     * 司机结算里程
     */
    private BigDecimal driverSettleMileage;

    /**
     * 轨迹里程
     */
    private BigDecimal trackMileage;

}
