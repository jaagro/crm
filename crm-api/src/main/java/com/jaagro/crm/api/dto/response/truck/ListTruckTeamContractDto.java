package com.jaagro.crm.api.dto.response.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 车队合同列表返回
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ListTruckTeamContractDto implements Serializable {
    /**
     * 主键车队合同表ID
     */
    private Integer id;

    /**
     * 关联车队表ID
     */
    private Integer truckTeamId;

    /**
     * 客户名称
     */
    private Integer customerName;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 业务类型(1 饲料运输 2 毛鸡运输 3 猪运输)
     */
    private Integer bussinessType;

    /**
     * 签约日期
     */
    private Date contractDate;

    /**
     * 合同开始时间
     */
    private Date startDate;

    /**
     * 合同结束时间
     */
    private Date endDate;

    /**
     * 合同状态(0-待审核 1-审核通过)
     */
    private Integer contractStatus;

    /**
     * 备注信息
     */
    private String notes;
}
