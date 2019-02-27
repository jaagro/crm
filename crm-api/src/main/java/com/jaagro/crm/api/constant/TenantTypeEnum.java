package com.jaagro.crm.api.constant;

/**
 * @author tonyZheng
 * @date 2019-02-27 10:12
 */
public enum TenantTypeEnum {

    /**
     * 租户类型
     */
    LOGISTICS(1, "LOGISTICS", "物流"),
    BREEDING(20, "BREEDING", "养殖");

    private int code;
    private String type;
    private String desc;

    TenantTypeEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (TenantTypeEnum type : TenantTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (TenantTypeEnum type : TenantTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (TenantTypeEnum type : TenantTypeEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static TenantTypeEnum toEnum(int code) {
        for (TenantTypeEnum type : TenantTypeEnum.values()) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
