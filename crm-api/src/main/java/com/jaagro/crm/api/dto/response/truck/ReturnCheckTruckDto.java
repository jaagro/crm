package com.jaagro.crm.api.dto.response.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ReturnCheckTruckDto implements Serializable {
    /**
     * 车牌号码
     */
    private String truckNumber;

    /**
     * 车辆品牌
     */
    private String vehicleBrand;

    /**
     * 最大载重
     */
    private BigDecimal capacity;

    /**
     * 装载数量
     */
    private Integer maximumLoad;

    /**
     * 车辆自重
     */
    private BigDecimal truckWeight;

    /**
     * 营运证号
     */
    private String businessPermit;

    /**
     * 行驶证号
     */
    private String drivingPermit;

    /**
     * 备注信息
     */
    private String notes;

}
