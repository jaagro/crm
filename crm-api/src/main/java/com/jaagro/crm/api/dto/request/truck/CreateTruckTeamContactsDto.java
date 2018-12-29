package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class CreateTruckTeamContactsDto implements Serializable {
    private static final long serialVersionUID = -2687018742675870115L;
    /**
     * 主键车队表联系人ID
     */
    private Integer id;

    /**
     * 关联车队表ID
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
