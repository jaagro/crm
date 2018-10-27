package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class CreateTruckQualificationByOneDto implements Serializable {
    private Integer truckTeamId;
    private Integer truckId;
    private Integer driverId;
    private UpdateTruckQualificationDto qualification;
}
