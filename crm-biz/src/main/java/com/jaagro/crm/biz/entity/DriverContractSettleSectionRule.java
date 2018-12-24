package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yj
 * @since 20181224
 */
@Data
@Accessors(chain = true)
public class DriverContractSettleSectionRule implements Serializable{
    private static final long serialVersionUID = -3236957753903386658L;
    /**
     * 司机合同结算里程区间配制表id
     */
    private Integer id;

    /**
     * 车队合同id
     */
    private Integer truckTeamContractId;

    /**
     * 司机合同结算配制表id
     */
    private Integer driverContractSettleRuleId;

    /**
     * 区间起始(不包含)
     */
    private BigDecimal start;

    /**
     * 区间结束(包含)
     */
    private BigDecimal end;

    /**
     * 区间类型,1-里程,2-重量
     */
    private Integer type;

    /**
     * 结算单价
     */
    private BigDecimal settlePrice;

    /**
     * 单位,1-元/吨,2-元/公里
     */
    private Integer unit;

    /**
     * 是否有效：1-有效 0-无效
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
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