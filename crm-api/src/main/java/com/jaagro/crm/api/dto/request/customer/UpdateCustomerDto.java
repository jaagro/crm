package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 修改客户帮助类
 *
 * @author tony
 */
@Data
@Accessors(chain = true)
public class UpdateCustomerDto implements Serializable {

    /**
     * 客户主键id
     */
    private Integer id;

    /**
     * 客户名称(个体客户时，就是自然人姓名)
     */
    private String customerName;

    /**
     * 客户类型
     * (1:个体客户 2:企业客户 )
     */
    private Integer customerType;

    /**
     * 统一社会验证码(个体客户时，就是自然人身份证号码)
     */
    private String creditCode;

    /**
     * 审核状态
     * (0未审核，1-正常合作  10-停止合作 11-审核未通过 13-作废)
     */
    private Integer customerStatus;

    /**
     * 所属省份
     */
    private String province;

    /**
     * 所属城市
     */
    private String city;

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
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 账期天数
     */
    private Integer billingPeriod;

    /**
     * 是否开票
     * 0:否 1:是
     */
    private Boolean enableInvoice;

    /**
     * 发票类型
     * 1:增值税普通发票 2:增值税专用发票
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
     * 备注信息(用车要求)
     */
    private String notes;

    /* *//**
     * 客户联系人
     *//*
    private List<UpdateCustomerContractDto> customerContractDtos;

    *//**
     * 收发货地址
     *//*
    private List<UpdateCustomerSiteDto> customerSites;

    *//**
     * 资质证件照
     *//*
    private List<UpdateCustomerQualificationDto> qualificationCertificDtos;

    *//**
     * 客户合同
     *//*
    private List<UpdateContractDto> createContractDtos;*/
}
