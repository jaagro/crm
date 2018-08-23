package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 客户联系人分页查询帮助类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ListCustomerContactsCriteriaDto implements Serializable {

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
     * 关键字
     */
    private String keywords;

}
