package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author
 * @since 20181224
 */
@Data
@Accessors(chain = true)
public class ContractOilPrice implements Serializable{
    private static final long serialVersionUID = -750031261152440233L;
    /**
     * 油价表id
     */
    private Integer id;

    /**
     * 合同id
     */
    private Integer contractId;

    /**
     * 合同类型,1-客户合同,2-司机合同
     */
    private Integer contractType;

    /**
     * 价格(元/升)
     */
    private BigDecimal price;

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
     * 创建人id
     */
    private Integer createUserId;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 更新人
     */
    private Integer modifyUserId;

    /**
     * 更新人姓名
     */
    private String modifyUserName;

}