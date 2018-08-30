package com.jaagro.crm.api.dto.response.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ReturnCheckTruckTeamDto implements Serializable {
    /**
     * 主键车队表ID
     */
    private Integer id;

    /**
     * 车队名称
     */
    private String teamName;

    /**
     * 车队类型(1 个体 2 自有 3 车队)
     */
    private Integer teamType;

    /**
     * 法人姓名
     */
    private String legalName;

    /**
     * 统一社会信用码（个体司机时，为司机身份证号码）
     */
    private String creditCode;

    /**
     * 车队状态(0；停止合作  1；审核未通过 2未审核，3正常合作)
     */
    private Integer teamStatus;
}
