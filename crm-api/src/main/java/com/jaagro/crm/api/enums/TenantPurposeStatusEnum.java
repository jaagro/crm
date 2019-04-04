package com.jaagro.crm.api.enums;

/**
 * @author @Gao.
 * 计划状态
 */

public enum TenantPurposeStatusEnum {
    /**
     * 计划状态类型
     */
    UN_DONE(1, "UN_DONE", "待处理"),
    DONE(2, "DONE", "已处理");
    private int code;
    private String type;
    private String desc;

    TenantPurposeStatusEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (TenantPurposeStatusEnum type : TenantPurposeStatusEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (TenantPurposeStatusEnum type : TenantPurposeStatusEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (TenantPurposeStatusEnum type : TenantPurposeStatusEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static TenantPurposeStatusEnum toEnum(int code) {
        for (TenantPurposeStatusEnum type : TenantPurposeStatusEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
