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
     *
     */
    private boolean flag;

    private List<ListDriverContractSettlelInfoDto> driverContractSettlelInfoDtos = new ArrayList<>();
}
