package com.jaagro.crm.api.dto.request.truck;

import com.jaagro.crm.api.dto.request.contract.CreateContractQualificationDto;
import com.jaagro.crm.api.dto.request.contract.CreateDriverContractSettleDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class CreateTruckTeamContractDto implements Serializable {
    private static final long serialVersionUID = 4773938326501732602L;
    /**
     * 关联车队表ID
     */
    private Integer truckTeamId;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 业务类型(1 饲料运输 2 毛鸡运输 3 猪运输)
     */
    private Integer bussinessType;

    /**
     * 合同开始时间
     */
    private Date startDate;

    /**
     * 合同结束时间
     */
    private Date endDate;

    /**
     * 备注信息
     */
    private String notes;

    /**
     * 合同资质证件照
     */
    private List<CreateContractQualificationDto> qualificationDtoList;

    /**
     * 创建运力合同报价
     */
    private CreateDriverContractSettleDto createDriverContractSettleDto;


}
