package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class CreateTruckTeamBankcardDto implements Serializable {
    private static final long serialVersionUID = -1219965403638788336L;
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
