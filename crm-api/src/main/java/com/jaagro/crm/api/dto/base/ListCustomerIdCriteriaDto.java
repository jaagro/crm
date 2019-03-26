package com.jaagro.crm.api.dto.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author @baiyiran
 */
@Data
@Accessors(chain = true)
public class ListCustomerIdCriteriaDto implements Serializable {
    /**
     * 客户姓名
     */
    private String customerName;

    /**
     *
     */
    private Integer tenantId;

}
