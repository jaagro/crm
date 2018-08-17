package com.jaagro.crm.api.constant;

/**
 * 客户审核状态
 *
 * @author liqiangping
 */
public final class CustomerStatus {

    /**
     * 未审核
     */
    public static final Integer UNCHECKED = 0;

    /**
     * 正常合作
     */
    public static final Integer NORMAL_COOPERATION = 1;

    /**
     * 审核未通过
     */
    public static final Integer AUDIT_FAILED = 2;

    /**
     * 停止合作
     */
    public static final Integer STOP_COOPERATION = 3;

}
