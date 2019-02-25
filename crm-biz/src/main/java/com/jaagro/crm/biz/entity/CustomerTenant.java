package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 * @date 201/2/21
 */
@Data
@Accessors(chain = true)
public class CustomerTenant implements Serializable {
    /**
     * 客户租户关系主键
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 租户id
     */
    private Integer tenantId;
}