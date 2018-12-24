package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class TruckTeamBankcard implements Serializable {
    private static final long serialVersionUID = 1442659632121489333L;
    /**
     * 车队银行表ID
     */
    private Integer id;

    /**
     * 关联车队表
     */
    private Integer truckTeamId;

    /**
     * 付款账号
     */
    private String paymentAccountId;

    /**
     * 开户银行
     */
    private String depositBank;

    /**
     * 开户人
     */
    private String accountPerson;
}