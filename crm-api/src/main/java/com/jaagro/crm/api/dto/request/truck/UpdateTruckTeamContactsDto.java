package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class UpdateTruckTeamContactsDto implements Serializable {
    /**
     * 车队ID
     */
    private Integer truckTeamId;

    /**
     * 联系人姓名
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 职位
     */
    private String position;
}
