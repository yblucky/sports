package com.xlf.common.enums;

/**
 *
 * 部门类型
 * Created by Administrator on 2017/8/17.
 */
public enum AreaEnum {

    DEPARTMENT_A("A", "部门A", "department_a"),
    DEPARTMENT_B("B", "部门B", "department_b");

    private String code;
    private String name;
    private String egName;

    private AreaEnum(String code, String name, String egName) {
        this.name = name;
        this.code = code;
        this.egName = egName;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEgName() {
        return egName;
    }

    public void setEgName(String egName) {
        this.egName = egName;
    }

    public static String getEgName(String code) {
        if (code == null) {
            return "";
        }
        for (AreaEnum enums : AreaEnum.values()) {
            if (enums.getCode().toString().equals(code)) {
                return enums.getEgName();
            }
        }
        return code;
    }

}
