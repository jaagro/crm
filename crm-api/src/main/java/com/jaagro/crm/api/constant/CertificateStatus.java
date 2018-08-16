package com.jaagro.crm.api.constant;

/**证件状态
 * @author liqiangping
 */
public final class CertificateStatus {

    /**
     * 未审核
     */
    public static final Integer UNCHECKED = 0;

    /**
     * 正常
     */
    public static final Integer NORMAL = 1;

    /**
     * 审核未通过
     */
    public static final Integer AUDIT_FAILED = 2;

    /**
     * 不可用
     */
    public static final Integer DISABLED= 4;
}
