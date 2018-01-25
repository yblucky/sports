package com.xlf.common.enums.sports;

/**
 * 流水业务类型
 * Created by Administrator on 2017/8/17.
 */
public enum BusnessTypeEnum {

    BACK_RECHARGE(11,"后台充值","back_recharge"),
    WITHDRAWALS(12,"提现","withdrawals"),
    TIME_BETTING(21,"时时彩投注","time_betting"),
    RACING_BETTING(22,"北京赛车投注","racing_betting"),
    ADD_KICKBACKAMOUNT_RECORD(31,"上级返水衡量值加","ADD_KICKBACKAMOUNT_RECORD"),
    REDUCE_KICKBACKAMOUNT_RECORD(32,"上级返水衡量值减","REDUCE_KICKBACKAMOUNT_RECORD"),
    FREE_FORZEN(41,"解除冻结","free_forzen");


    private Integer code;
    private String name;
    private String egName;

    private BusnessTypeEnum(Integer code, String name, String egName) {
        this.name = name;
        this.code = code;
        this.egName = egName;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getEgName() {
        return egName;
    }

    public static String getName(Integer code){
        if (code == null) {
            return "";
        }
        for(BusnessTypeEnum enums : BusnessTypeEnum.values()){
            if (enums.getCode().equals(code)) {
                return enums.getName();
            }
        }
        return String.valueOf(code);
    }
}
