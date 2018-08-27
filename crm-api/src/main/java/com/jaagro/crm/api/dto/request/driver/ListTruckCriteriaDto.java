package com.jaagro.crm.api.dto.request.driver;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ListTruckCriteriaDto implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private String truckNumber;
    private Integer truckTeamId;
}
