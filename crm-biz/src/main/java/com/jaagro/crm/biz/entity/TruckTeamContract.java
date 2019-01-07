package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class TruckTeamContract implements Serializable {
    private static final long serialVersionUID = -1553939985741704500L;
    /**
     * 主键车队合同表ID
     */
    private Integer id;

    /**
     * 关联车队表ID
     */
    private Integer truckTeamId;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 业务类型(1 饲料运输 2 毛鸡运输 3 仔猪 4生猪)
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

    /**
     * 新建时间
     */
    private Date createTime;

    /**
     * 新建人
     */
    private Integer createUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;
}