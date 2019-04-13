package com.jaagro.crm.api.dto.request.league;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description: 养殖意向处理
 * @author: @Gao.
 * @create: 2019-04-10 11:22
 **/
@Data
@Accessors(chain = true)
public class PurposeDisposeDto implements Serializable {
    /**
     * 养殖意向id
     */
    private Integer tenantPurposeId;
    /**
     * 备注
     */
    private String notes;
}
