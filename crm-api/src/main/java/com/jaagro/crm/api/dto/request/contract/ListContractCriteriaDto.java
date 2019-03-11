package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ListContractCriteriaDto implements Serializable {

    private static final long serialVersionUID = -2518378905883744370L;
    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 客户Id
     */
    private Integer customerId;

    /**
     * 租户Id
     */
    private Integer tenantId;

    /**
     * 合同状态: 1-正常 2-终止
     */
    private Integer contractStatus;

}
