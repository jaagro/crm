package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 修改收发货帮助类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class UpdateCustomerSiteDto implements Serializable {

    /**
     * 客户发货ID
     */
    private Integer id;

    /**
     * 地址类型
     * 1-装货点，2-卸货点
     */
    private Integer siteType;

    /**
     * 归属网点
     */
    private Integer deptId;

    /**
     * 养殖场类型（装货地属性）：1-平养场 2-网养场 3-笼养场
     */
    private Integer farmsType;

    /**
     * 作业时间（装货地属性）
     */
    private Integer operationTime;

    /**
     * 屠宰链数(卸货地属性)
     */
    private Integer killChain;

    /**
     * 开始屠宰时间（卸货地属性）
     */
    private Date killTime;

    /**
     * 外键关联客户ID
     * ( References customer)
     */
    private Integer customerId;

    /**
     * 装货地名称
     */
    private String siteName;

    /**
     * 系统状态(1-可用，2-不可用)
     */
    private Integer siteStatus;

    /**
     * 联系人姓名
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String county;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 装货要求
     */
    private String shipmentRequire;

    /**
     * 备注消息
     */
    private String notes;

}
