package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class CreateContractDto implements Serializable {

    private Long id;

    private Long customerId;

    private Integer contractStatus;

    private Date startDate;

    private Date endDate;

    private Integer type;

    private String theme;

    private String product;

    private String context;

    private String contractNumber;

    private String remark;

    private Date createTime;

    private Long createUser;

    private Date newUpdateTime;

    private Long newUpdateUser;

    private Integer version;

    private List<ContractPriceDto> price;
}
