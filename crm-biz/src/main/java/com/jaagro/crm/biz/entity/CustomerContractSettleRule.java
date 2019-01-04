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
public class CustomerContractSettleRule implements Serializable{
    private static final long serialVersionUID = 5007749668636522029L;
    /**
     * 客户结算配制表id
     */
    private Integer id;

    /**
     * 合同id
     */
    private Integer customerContractId;

    /**
     * 起始里程(公里,不包含)
     */
    private BigDecimal startMileage;

    /**
     * 结束里程(包含)
     */
    private BigDecimal endMileage;

    /**
     * 生效时间
     */
    private Date effectiveTime;

    /**
     * 失效时间
     */
    private Date invalidTime;

    /**
     * 是否为历史配制 0-否,1-是
     */
    private Boolean historyFlag;

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
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;

}