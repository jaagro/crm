package com.jaagro.crm.api.dto.request.contract;

import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;
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

    private static final long serialVersionUID = 2729052832985945646L;
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
     * 合同资质证件照
     */
    private List<CreateContractQualificationDto> qualificationDtos;

    /**
     * 结算基础信息
     */
    private List<CreateCustomerSettlePriceDto> settlePriceDtoList;

    /**
     * 油价设置
     */
    private CreateContractOilPriceDto oilPriceDto;

    /**
     * 结算配制
     */
    private CreateCustomerSettleRuleDto settleRuleDto;

}
