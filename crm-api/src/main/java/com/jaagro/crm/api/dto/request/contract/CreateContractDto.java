package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class CreateContractDto implements Serializable {

    /**
     * 客户ID
     */
    private Integer customerId;

    /**
     * 合同开始时间
     */
    private Date startDate;

    /**
     * 合同结束时间
     */
    private Date endDate;

    /**
     * 签约日期
     */
    private Date contractDate;

    /**
     * 合同类型
     */
    private Integer type;

    /**
     * 合同主题
     */
    private String theme;

    /**
     * 产品
     */
    private String product;

    /**
     * 合同正文
     */
    private String context;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 报价
     */
    private List<CreateContractPriceDto> price;

    /**
     * 资质证件照
     */
    private List<CreateContractQualificationDto> qualificationDtos;
}
