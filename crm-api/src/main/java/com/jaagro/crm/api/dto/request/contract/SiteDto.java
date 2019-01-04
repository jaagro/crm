package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gavin
 * 20181219
 */
@Data
@Accessors(chain = true)
public class SiteDto implements Serializable {

    private static final long serialVersionUID = -5659749204859697599L;

    /**
     * 装货地址id
     */
    @NotNull(message = "{loadSiteId.NotNull}")
    private Integer loadSiteId;

    /**
     * 卸货地址id
     */
    @NotNull(message = "{unloadSiteId.NotNull}")
    private Integer unloadSiteId;

}
