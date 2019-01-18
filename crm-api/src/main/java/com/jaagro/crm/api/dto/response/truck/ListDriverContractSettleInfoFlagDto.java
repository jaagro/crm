package com.jaagro.crm.api.dto.response.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author @Gao.
 */
@Data
@Accessors(chain = true)
public class ListDriverContractSettleInfoFlagDto implements Serializable {
    /**
     * 用于区分该类型已经全部显示
     */
    private boolean flag;

    private List<ListDriverContractSettleInfoDto> driverContractSettleInfoDtos = new ArrayList<>();
}
