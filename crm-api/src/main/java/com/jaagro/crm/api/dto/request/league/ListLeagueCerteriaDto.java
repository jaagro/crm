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
public class ListLeagueCerteriaDto implements Serializable {

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 姓名
     */
    private String leagueName;

}
