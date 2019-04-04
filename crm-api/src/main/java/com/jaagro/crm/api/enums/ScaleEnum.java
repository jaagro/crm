package com.jaagro.crm.api.enums;

public enum ScaleEnum {
    /**
     * 计划状态类型
     */
    ONE(1, "ONE", "1~15"),
    FIFTEEN(2, "FIFTEEN", "16~100"),
    HUNDRED(3, "HUNDRED", "100~200"),
    TWO_HUNDRED(4, "TWO_HUNDRED", "201~1000"),
    THOUSAND(5, "THOUSAND", "1001~2000"),
    ABOVE(6, "ABOVE", "2001以上");


    private int code;
    private String type;
    private String desc;

    ScaleEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (ScaleEnum type : ScaleEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (ScaleEnum type : ScaleEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (ScaleEnum type : ScaleEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static ScaleEnum toEnum(int code) {
        for (ScaleEnum type : ScaleEnum.values()) {
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
