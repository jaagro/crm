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
public class ListContractCriteriaDto implements Serializable {

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 合同状态: 1-正常 2-终止
     */
    private Integer contractStatus;

    /**
     * 合同类型
     */
    private Integer type;

    /**
     * 关键字
     */
    private String keywords;
}
