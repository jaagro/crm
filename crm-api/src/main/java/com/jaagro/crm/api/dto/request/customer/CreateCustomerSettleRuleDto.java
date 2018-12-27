package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 客户合同结算配制主表dto
 *
 * @author baiyiran
 * @Date 2018/12/25
 */
@Accessors(chain = true)
@Data
public class CreateCustomerSettleRuleDto implements Serializable {

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
     * 车辆设置
     */
    private List<CreateCustomerSettleTruckRuleDto> truckRuleDtoList;

    /**
     * 里程区间
     */
    private List<CreateCustomerSectionRuleDto> sectionRuleDtoList;

}
