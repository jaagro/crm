package com.jaagro.crm.api.dto.request.driver;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class CreateListTruckQualificationDto implements Serializable {
    private Integer truckTeamId;
    private Integer truckId;
    private Integer driverId;
    private List<CreateTruckQualificationDto> qualification;
}
