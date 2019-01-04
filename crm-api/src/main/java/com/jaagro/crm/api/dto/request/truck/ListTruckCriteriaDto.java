package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ListTruckCriteriaDto implements Serializable {
    private static final long serialVersionUID = 7058830841683261791L;
    private Integer pageNum;
    private Integer pageSize;
    private String truckNumber;
    private String keywords;
    private Integer truckTeamId;
    private Integer teamType;
    private Integer truckStatus;
    private Integer truckTypeId;

}
