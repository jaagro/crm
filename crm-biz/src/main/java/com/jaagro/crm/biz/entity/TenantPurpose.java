package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class TenantPurpose implements Serializable {
    /**
     * 租户意向表id
     */
    private Integer id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 联系人名称
     */
    private String contractName;

    /**
     * 联系人手机号
     */
    private String phoneNumber;

    /**
     * 状态1-待处理2-已处理
     */
    private Integer status;

    /**
     * 规模
     */
    private Integer scale;

    /**
     * 邮箱地址
     */
    private String emailAddress;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否有效(0-无效,1-有效)
     */
    private Boolean enable;
}