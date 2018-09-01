package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author baiyiarn
 */
@Data
@Accessors(chain = true)
public class CreateCustomerDto implements Serializable {

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
    private List<CreateCustomerContactsDto> contracts;

    *//**
     * 收发货地址
     *//*
    private List<CreateCustomerSiteDto> customerSites;

    *//**
     * 资质证件照
     *//*
    private List<CreateCustomerQualificationDto> qualificationCertificDtos;

    *//**
     * 客户合同
     *//*
    private List<CreateContractDto> createContractDtos;*/

}
