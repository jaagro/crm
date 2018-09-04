package com.jaagro.crm.api.dto.response.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ListTruckDto implements Serializable {
    /**
     * 主键车辆表ID
     */
    private Integer truckId;

    /**
     * 关联车队表ID
     */
    private Integer truckTeamId;

    /**
     * 车队名称
     */
    private String teamName;

    /**
     * 车队类型
     */
    private String teamType;

    /**
     * 车牌号码
     */
    private String truckNumber;

    /**
     * 市
     */
    private String city;

    /**
     * 车型
     */
    private String typeName;

    /**
     * 车长
     */
    private String truckLength;

    /**
     * 备注信息
     */
    private Integer truckStatus;

}
