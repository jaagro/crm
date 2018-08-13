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
public class ContractLog implements Serializable {

    private Long id;

    private Long contractId;

    private Integer pricingId;

    private Integer selectionId;

    private Integer logType;

    private String logContent;

    private Date createTime;

    private Integer modifyUserId;
}