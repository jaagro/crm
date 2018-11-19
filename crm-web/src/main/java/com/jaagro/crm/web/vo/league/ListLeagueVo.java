package com.jaagro.crm.web.vo.league;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author baiyiran
 * @Date 2018/11/15
 */
@Data
@Accessors(chain = true)
public class ListLeagueVo implements Serializable {
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
