package com.jaagro.crm.api.dto.response.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 客户合同分页查询 返回类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)

public class ReturnContractDto implements Serializable {
    /**
     * 合同ID
     */
    private Integer id;

    /**
     * 客户ID
     */
    private Integer customerId;

    /**
     * 合同状态: 1-正常 2-终止
     */
    private Integer contractStatus;

    /**
     * 合同开始时间
     */
    private Date startDate;

    /**
     * 合同结束时间
     */
    private Date endDate;

    /**
     * 签约日期
     */
    private Date contractDate;

    /**
     * 合同类型
     */
    private Integer type;

    /**
     * 合同主题
     */
    private String theme;

    /**
     * 产品
     */
    private String product;

    /**
     * 合同正文
     */
    private String context;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 最新修改时间
     */
    private Date newUpdateTime;

    /**
     * 最新修改人
     */
    private Integer newUpdateUser;

    /**
     * version
     */
    private Integer version;

    /**
     * 合同报价
     */
    private List<ReturnContractPriceDto> prices;

}
