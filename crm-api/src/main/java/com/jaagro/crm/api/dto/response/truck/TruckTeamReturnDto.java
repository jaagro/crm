package com.jaagro.crm.api.dto.response.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class TruckTeamReturnDto implements Serializable {
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

    /**
     * 车队合作类型(1-自有  2-加盟 3-－外请是啥？请产品确认)
     */
    private Integer cooperationType;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区县
     */
    private String county;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 修改日期
     */
    private Date modifyTime;

    /**
     * 创建人(关联用户ID)
     */
    private Integer createUserId;

    /**
     * 修改人(关联用户ID)
     */
    private Integer modifyUserId;

    /**
     * 备注信息
     */
    private String notes;
}
