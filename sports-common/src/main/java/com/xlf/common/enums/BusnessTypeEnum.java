package com.xlf.common.enums;

/**
 * 流水业务类型
 * Created by Administrator on 2017/8/17.
 */
public enum BusnessTypeEnum {

    BACK_RECHARGE (11, "后台充值", "back_recharge"),
    AGENT_RECHARGE (112, "代理充值", "back_recharge"),
    WITHDRAWALS (12, "提现", "withdrawals"),
    WITHDRAWALS_FAIL (13, "提现驳回", "withdrawals fail"),
    TIME_BETTING (21, "时时彩投注", "time_betting"),
    TIME_LOTTERY (22, "时时彩开奖", "time_lottery"),
    TIME_UNDO (23, "时时彩撤单", "time_undo"),
    RACING_BETTING (31, "北京赛车投注", "racing_betting"),
    RACING_LOTTERY (32, "北京赛车开奖", "racing_lottery"),
    RACING_UNDO (33, "北京赛车撤单", "racing_undo"),
    ADD_KICKBACKAMOUNT_RECORD (41, "上级返水衡量值加", "add_kickbackamount_record"),
    REDUCE_KICKBACKAMOUNT_RECORD (42, "上级返水衡量值减", "reduce_kickbackamount_record"),
    FREE_FORZEN (41, "解除冻结", "free_forzen"),
    RETURN_WATER (51, "代理返水", "return_water");


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

    public static String getName(Integer code) {
        if (code == null) {
            return "";
        }
        for (BusnessTypeEnum enums : BusnessTypeEnum.values ()) {
            if (enums.getCode ().equals (code)) {
                return enums.getName ();
            }
        }
        return String.valueOf (code);
    }
    public static String getEgName(Integer code) {
        if (code == null) {
            return "";
        }
        for (BusnessTypeEnum enums : BusnessTypeEnum.values ()) {
            if (enums.getCode ().equals (code)) {
                return enums.getEgName ();
            }
        }
        return String.valueOf (code);
    }
}
