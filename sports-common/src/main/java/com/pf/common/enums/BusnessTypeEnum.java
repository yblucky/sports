package com.pf.common.enums;

/**
 * 流水业务类型
 * Created by Administrator on 2017/8/17.
 */
public enum BusnessTypeEnum {

    EP_RECHARGE(11,"EP充值","EP_recharge"),
    EP_TRANSFER_IN(12,"EP转入","EP_transfer_in"),
    EP_TRANSFER_OUT(13,"EP转出","EP_transfer_out"),
    EP_EXCHANGE(14,"EP兑换","EP_exchange"),
    EP_WITHDRAWALS(15,"EP提现","EP_withdrawals"),
    E_ASSET_ACTIVE(16,"E资产激活","e_asset_active"),
    E_REWARD(17,"E资产赏金","E_reward"),
    E_RELEASE(18,"E资产释放","E_release"),
    BIRDSCORE_TRANSFER_IN(19,"候鸟积分转入","birdscore_transfer_in"),
    BIRDSCORE_TRANSFER_OUT(20,"候鸟积分转出","birdscore_transfer_out"),
    ACCOUNT_ACTIVE(21,"激活账号","Account_active"),
    EP_FREE_FORZEN(22,"EP解除冻结","EP_free_forzen"),
    ACCOUNT_ACTIVE_CHARGE(23,"充值激活次数","Account_active_charge");

/*    EP_RECHARGE(11,"EP充值","EP_recharge"),
    EP_TRANSFER_IN(12,"EP转入","EP_transfer_in"),
    EP_TRANSFER_OUT(13,"EP转出","EP_transfer_out"),
    EP_EXCHANGE(14,"EP兑换","EP_exchange"),
    EP_WITHDRAWALS(15,"EP提现","EP_withdrawals"),
    E_ASSET_ACTIVE(16,"E资产激活","e_asset_active"),
    E_REWARD(17,"E资产赏金","E_reward"),
    E_RELEASE(18,"E资产释放","E_release"),
    BIRDSCORE_TRANSFER_IN(19,"候鸟积分转入","birdscore_transfer_in"),
    BIRDSCORE_TRANSFER_OUT(20,"候鸟积分转出","birdscore_transfer_out"),
    ACCOUNT_ACTIVE(21,"激活账号","Account_active"),
    EP_FREE_FORZEN(22,"EP解除冻结","EP_free_forzen"),
    ACCOUNT_ACTIVE_CHARGE(23,"充值激活次数","Account_active_charge");*/

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
    public static String getEgName(String code){
        if (code == null) {
            return "";
        }
        for(BusnessTypeEnum enums : BusnessTypeEnum.values()){
            if (enums.getCode().toString().equals(code)) {
                return enums.getEgName();
            }
        }
        return code;
    }



}
