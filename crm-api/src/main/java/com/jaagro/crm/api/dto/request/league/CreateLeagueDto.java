package com.jaagro.crm.api.dto.request.league;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 * @Date 2018/11/14
 */
@Data
@Accessors(chain = true)
public class CreateLeagueDto implements Serializable {
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
}
