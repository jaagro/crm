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
public class Contract implements Serializable {

    /**
     * id
     */
    private Long id;

    private Long customerId;

    private Integer contractStatus;

    private Date startDate;

    private Date endDate;

    private Integer type;

    private String theme;

    private String product;

    private String context;

    private String contractNumber;

    private String remark;

    private Date createTime;

    private Long createUser;

    private Date newUpdateTime;

    private Long newUpdateUser;

    private Integer version;
}