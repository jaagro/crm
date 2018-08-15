package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class Customer implements Serializable {
    /**
     * 客户主键id
     */
    private Long id;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户类型
 (1:个体客户 2:企业客户 )
     */
    private Integer customerType;

    /**
     * 审核状态
 (0未审核，1正常合作 2审核未通过，4停止合作)
     */
    private Integer customerStatus;

    /**
     * 归属网点(References: branch)
     */
    private Long branchId;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 所属省份
     */
    private String province;

    /**
     * 所属区县
     */
    private String county;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 纬度
     */
    private Long latitude;

    /**
     * 经度
     */
    private Long longitude;

    /**
     * 账期天数
     */
    private Integer billingPeriod;

    /**
     * 是否开票
     0:否 1:是
     */
    private Byte enableInvoice;

    /**
     * 发票类型
 1:增值税普通发票 2:增值税专用发票
     */
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceHeader;

    /**
     * 税务编号
     */
    private String taxNumber;

    /**
     * 备注信息
     */
    private String notes;

    /**
     * 创建日期
     */
    private Date createdTime;

    /**
     * 修改日期
     */
    private Date modifyTime;

    /**
     * 创建人(References: user)
     */
    private Long createdUserId;

    /**
     * 修改人(References: user)
     */
    private Long modifyUserId;

    /**
     * 规模信息
     */
    private String scaleMessage;

}