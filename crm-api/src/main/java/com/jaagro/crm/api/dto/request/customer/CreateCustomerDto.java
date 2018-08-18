package com.jaagro.crm.api.dto.request.customer;

import com.jaagro.crm.api.dto.request.contract.CreateContractDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class CreateCustomerDto implements Serializable {
    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户类型
     * (1:个体客户 2:企业客户 )
     */
    private Integer customerType;

    /**
     * 审核状态
     * (0未审核，1正常合作 2审核未通过，4停止合作)
     */
    private Integer customerStatus;

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

    /**
     * 是否删除 0:否 1:是
     */
    private Byte enable;

    /**
     * 客户联系人
     */
    private List<CreateCustomerContractDto> contracts;

    /**
     * 收发货地址
     */
    private List<CreateCustomerSiteDto> customerSites;

    /**
     * 资质证件照
     */
    private List<CreateQualificationCertificDto> qualificationCertificDtos;

    /**
     * 客户合同
     */
    private List<CreateContractDto> createContractDtos;


}
