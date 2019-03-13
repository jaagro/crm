package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author gavin
 */
@Data
@Accessors(chain = true)
public class QueryTruckDto implements Serializable {
    private static final long serialVersionUID = 1701261160076190785L;
    private Integer pageNum;
    private Integer pageSize;
    private String truckNumber;
    private Integer truckTypeId;
    /**
     * 车辆所属网点id
     */
    private Integer netWorkId;
    /**
     * 货物类型
     */
    private Integer goodsType;
    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区县
     */
    private String county;

}
