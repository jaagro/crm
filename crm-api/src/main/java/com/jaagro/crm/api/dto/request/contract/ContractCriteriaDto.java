package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ContractCriteriaDto implements Serializable {

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    private Long customerId;

    private Integer contractStatus;

    private Date startDate;

    private Date endDate;

    private Integer type;

    private String theme;

    private Date createTime;

}
