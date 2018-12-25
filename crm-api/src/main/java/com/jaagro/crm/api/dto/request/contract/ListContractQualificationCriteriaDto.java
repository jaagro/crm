package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ListContractQualificationCriteriaDto implements Serializable {

    private static final long serialVersionUID = -5637048351778941351L;
    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 关联类型：1-客户合同 2-司机合同
     */
    private Integer relevanceType;

    /**
     * 关联id
     */
    private Integer relevanceId;

    /**
     * 证件状态(0-未审核。1-正常 2-审核未通过审核 4-不可用)
     */
    private Integer certificateStatus;

    /**
     * 用来区分查看详情还是获取待审核下一条
     */
    private String enableCheck;

}
