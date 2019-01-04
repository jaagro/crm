package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ListTruckQualificationCriteriaDto implements Serializable {
    private static final long serialVersionUID = -128570102815998334L;
    private Integer pageNum;
    private Integer pageSize;
    private Integer truckTeamId;
    private Integer truckId;
    private Integer driverId;
    private Integer certificateStatus;
    /**
     * 用来区分查看详情还是获取待审核下一条
     */
    private String enableCheck;

}
