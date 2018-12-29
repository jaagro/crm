package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ListTruckTeamContractCriteriaDto implements Serializable {
    private static final long serialVersionUID = -4071829474511812010L;
    /**
     * 起始页
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 车队id
     */
    private Integer truckTeamId;

}
