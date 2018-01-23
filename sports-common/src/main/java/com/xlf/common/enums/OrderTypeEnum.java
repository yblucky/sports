package com.xlf.common.enums;

/**
 * 订单类型
 * Created by Administrator on 2017/8/21.
 */
public enum OrderTypeEnum {

    RECHARGE("10","充值"),
    EXCHANGE("20","提现"),
    COINORDER("30","数字资产");

    private String code;
    private String name;

    private OrderTypeEnum(String code, String name) {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(String code){
        if (code == null) {
            return "";
        }
        for(OrderTypeEnum enums : OrderTypeEnum.values()){
            if (enums.getCode().equals(code)) {
                return enums.getName();
            }
        }
        return code;
    }

}
