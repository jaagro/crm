package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
     * 客户名称
     */
    private String customerName;

    /**
     * 联系人电话
     */
    private String phone;

    /**
     * 客户类型
     * (1:个体客户 2:企业客户 )
     */
    private Integer customerType;

    /**
     * 审核状态
     */
    private Integer customerStatus;

}

