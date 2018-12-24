package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售机会
 *
 * @author baiyiran
 * @date 2018/11/14
 */
@Data
@Accessors(chain = true)
public class League implements Serializable {
    private static final long serialVersionUID = -7905420828702376035L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 销售机会类型
     */
    private String leagueType;

    /**
     * 姓名
     */
    private String leagueName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 企业名称
     */
    private String company;

    /**
     * 详细内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

}