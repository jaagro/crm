package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 新增帮助查询类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class SiteDto implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 装货地名称
     */
    private String siteName;

}
