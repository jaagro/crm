package com.jaagro.crm.api.dto.response.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author gavin
 */
@Data
@Accessors(chain = true)
public class ListTruckWithDriversDto implements Serializable {
    private ListTruckDto listTruckDto;
    private List<DriverReturnDto> drivers;
}
