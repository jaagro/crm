package com.jaagro.crm.api.dto.response.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class TruckTypeReturnDto implements Serializable {
    /**
     * 车型表主键id
     */
    private Integer id;

    /**
     * 车型名称
     */
    private String typeName;

    /**
     * 车长
     */
    private String truckLength;

    /**
     * 车辆载重
     */
    private String truckWeight;

    /**
     * 承载总数
     */
    private String truckAmount;

    /**
     * 是否有效
     */
    private Boolean enabled;
}
