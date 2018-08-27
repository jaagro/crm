package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 收发货地址分页查询帮助类
 *
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ListSiteCriteriaDto implements Serializable {

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
     * 客户id
     */
    private Integer customerId;
    /**
     * 地点类型
     */
    private Integer siteType;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;
}
