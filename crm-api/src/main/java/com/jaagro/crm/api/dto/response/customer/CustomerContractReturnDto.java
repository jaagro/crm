package com.jaagro.crm.api.dto.response;

import java.io.Serializable;
import java.util.Date;

/**返回的客户合同
 * @author liqiangping
 */
public class CustomerContractReturnDto implements Serializable {

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
}
