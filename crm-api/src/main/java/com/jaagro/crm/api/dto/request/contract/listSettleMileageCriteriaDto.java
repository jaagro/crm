package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author baiyiran
 * @Date 2018/12/29
 */
@Data
@Accessors(chain = true)
public class listSettleMileageCriteriaDto implements Serializable {

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 项目部id
     */
    private Integer deptId;

    /**
     * 项目部名称
     */
    private String deptName;

    /**
     * 登陆人所在部门和下属所有部门
     */
    private List<Integer> departIds;
}
