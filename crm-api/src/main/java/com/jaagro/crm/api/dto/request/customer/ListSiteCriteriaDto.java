package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ListSiteCriteriaDto implements Serializable {

    private Integer pageNum;

    private Integer pageSize;

    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 地点类型
     */
    private Integer siteType;
}
