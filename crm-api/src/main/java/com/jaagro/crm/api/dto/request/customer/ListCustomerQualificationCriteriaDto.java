package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 资质证件照分页查询帮助类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ListCustomerQualificationCriteriaDto implements Serializable {

    /**
     * 起始页
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 证件类型(1-工商执照 2-身份证正面 3-身份证反面 4-......)
     */
    private Integer certificateType;

    /**
     * 证件状态(0-未审核。1-正常 2-审核未通过审核 4-不可用)
     */
    private Integer certificateStatus;

    /**
     * 是否可用（0不可用 1可用）
     */
    private Boolean enabled;
}
