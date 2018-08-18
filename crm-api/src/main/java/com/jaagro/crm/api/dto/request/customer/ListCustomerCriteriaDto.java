package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户分页查询帮助类
 *
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ListCustomerCriteriaDto implements Serializable {
    /**
     * 起始页
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

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
     * 发票类型
     * 1:增值税普通发票 2:增值税专用发票
     */
    private Integer invoiceType;
}

