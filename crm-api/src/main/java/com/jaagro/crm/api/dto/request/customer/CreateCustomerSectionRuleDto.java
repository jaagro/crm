package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
@Accessors(chain = true)
@Data
public class CreateCustomerSectionRuleDto implements Serializable {

    /**
     * 客户合同id
     */
    private Integer customerContractId;

    /**
     * 客户合同结算配制表id
     */
    private Integer customerContractSettleRuleId;

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
     * 价格
     */
    private BigDecimal settlePrice;

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
