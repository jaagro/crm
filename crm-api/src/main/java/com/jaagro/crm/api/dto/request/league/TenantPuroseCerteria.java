package com.jaagro.crm.api.dto.request.league;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description: 养殖体验意向
 * @author: @Gao.
 * @create: 2019-04-04 10:16
 **/
@Data
@Accessors(chain = true)
public class TenantPuroseCerteria implements Serializable {
    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 客户基本信息查询字段
     */
    private String customerInfoKey;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 状态
     */
    private Integer status;
}
