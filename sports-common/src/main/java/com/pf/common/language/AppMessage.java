package com.pf.common.language;

/**
 * Created by Administrator on 2017/9/19.
 */
public class AppMessage {
    /**
     * 手机号不能为空
     */
    public static String PHONE_NOT_NULL = "phone_not_null";
    /**
     * 登录密码不能为空
     */
    public static String LOGINPWD_NOT_NULL = "loginPwd_not_null";
    /**
     * 登录密码错误
     */
    public static String LOGINPWD_FAIL = "loginpwd_fail";
    /**
     * 登录名不能为空
     */
    public static String LOGINNAME_NOT_NULL = "loginName_not_null";
    /**
     * 用户昵称不能为空
     */
    public static String NICKNAME_NOT_NULL = "nickName_not_null";
    /**
     * 邀请码不能为空
     */
    public static String INVITECODE_NOT_NULL = "inviteCode_not_null";
    /**
     * 支付密码不能为空
     */
    public static String PAYPWD_NOT_NULL = "payPwd_not_null";
    /**
     * 短信验证码不能为空
     */
    public static String VERIFY_NOT_NULL = "verify_not_null";
    /**
     * 国家代码不能为空
     */
    public static String AREACODE_NOT_NULL = "areaCode_not_null";
    /**
     * 短信验证码已失效
     */
    public static String VERIFY_INVALID = "verify_invalid";
    /**
     * 短信验证码输入不正确
     */
    public static String VARIFY_FAIL = "varify_fail";
    /**
     * 用户昵称已被使用
     */
    public static String NICKNAME_HAS_USE = "nickName_has_use";
    /**
     * 邀请人不存在或者已被禁用
     */
    public static String INVITECODE_HAS_USE = "inviteCode_has_use";
    /**
     * 用户信息保存失败
     */
    public static String USER_SAVE_FAIL = "user_save_fail";
    /**
     * 该手机号已被注册
     */
    public static String PHONE_HAS_USE = "phone_has_use";
    /**
     * 用户不存在
     */
    public static String USER_INVALID = "user_invalid";
    /**
     * 用户已禁用
     */
    public static String USER_DISABLE = "user_disable";
    /**
     * 用户已激活
     */
    public static String USER_ACTIVE = "user_active";
    /**
     * 用户不是未激活状态
     */
    public static String USER_IS_NOT_UNACTIVE = "user_is_not_unactive";
    /**
     * 用户未激活
     */
    public static String USER_UNACTIVE = "user_unactive";
    /**
     * 因您的帐号信用等级为零，已被系统冻结，暂时无法登录
     */
    public static String USER_CREDIT_DISABLE = "user_credit_disable";
    /**
     * 用户登录不成功
     */
    public static String LOGIN_FAIL = "login_fail";
    /**
     * UID或手机号码错误
     */
    public static String UID_PHONE_FAIL = "uid_phone_fail";
    /**
     * 用户已被禁用不能修改密码
     */
    public static String USER_NOT_UPPWD = "user_not_upPwd";
    /**
     * 验证码失效或输入验证码错误
     */
    public static String VERIFY_ERROR = "verify_error";
    /**
     * 找回密码失败
     */
    public static String BACK_PWD_FAIL = "back_pwd_fail";
    /**
     * 用户UID或手机号不能为空
     */
    public static String UID_PHONE_NOT_NULL = "uid_phone_not_null";
    /**
     * 新密码不能为空
     */
    public static String NEW_PWD_NOT_NULL = "new_pwd_not_null";

    /**
     * 兑换数量必须大于1
     */
    public static String EXCHANGE_COUNT_ERROR = "exchange_count_error";
    /**
     * 兑换类型不正确
     */
    public static String EXCHANGE_TYPE_ERROR = "exchange_type_error";

    /**
     * 获取手机验证码失败
     */
    public static String LOADVERIFY_ERROR = "loadVerify_error";
    /**
     * 请选择收款人
     */
    public static String SELECT_PAYEE = "select_payee";
    /**
     * 请选择银行卡
     */
    public static String SELECT_BANKCARD = "select_bankCard";
    /**
     * 请选择充值金额
     */
    public static String SELECT_RECHARGE_AMOUNT = "select_recharge_amount";
    /**
     * 请不要输入含有emoji的字符
     */
    public static String THE_EMOJI_ERROR = "the_emoji_error";
    /**
     * 旧支付密码不能为空
     */
    public static String OLDPAYPWD_NOT_NULL = "oldPayPwd_not_null";
    /**
     * 新支付密码不能为空
     */
    public static String NEWPAYPWD_NOT_NULL = "newPayPwd_not_null";
    /**
     * 旧支付密码错误
     */
    public static String OLDPAYPWD_ERROR = "oldPayPwd_error";
    /**
     * 支付密码错误
     */
    public static String PAYPWD_ERROR = "paypwd_error";
    /**
     * 旧登录密码不能为空
     */
    public static String OLDLOGINPWD_NOT_NULL = "oldLoginPwd_not_null";
    /**
     * 新登录密码不能为空
     */
    public static String NEWLOGINPWD_NOT_NULL = "newLoginPwd_not_null";

    /**
     * 昵称不能含有emoji表情
     */
    public static String NICKNAME_EMOJI_ERROR = "nickName_emoji_error";
    /**
     * 头像不能为空
     */
    public static String HEADIMG_NOT_NULL = "headImg_not_null";
    /**
     * 银行卡号不能为空
     */
    public static String BANKCARD_NOT_NULL = "bankCard_not_null";
    /**
     * 开户银行不能为空
     */
    public static String BRANCH_NOT_NULL = "branch_not_null";
    /**
     * 持卡人姓名不能为空
     */
    public static String USERNAME_NOT_NULL = "userName_not_null";
    /**
     * 持卡人姓名不能含有emoji表情
     */
    public static String USERNAME_EMOJI_ERROR = "userName_emoji_error";
    /**
     * 开户支行不能含有emoji表情
     */
    public static String BRANCH_EMOJI_ERROR = "branch_emoji_error";
    /**
     * 未选择要修改的银行卡
     */
    public static String NOT_SELECT_UPDBANK = "not_select_updBank";
    /**
     * 未选择要删除的银行卡
     */
    public static String NOT_SELECT_DELBANK = "not_select_delBank";
    /**
     * 用户未输入投诉或建议内容
     */
    public static String USER_SUGGEST_ERROR = "user_suggest_error";
    /**
     * 内容不能含有emoji表情
     */
    public static String CONTENT_EMOJI_ERROR = "content_emoji_error";
    /**
     * 输入的手机号码与绑定的手机号不一致
     */
    public static String TWO_PHONE_EQUAL_FAIL = "two_phone_equal_fail";
    /**
     * 收款金额不能为空
     */
    public static String PAYEE_AMOUNT_NOT_NULL = "payee_amount_not_null";
    /**
     * EP兑换金额不能为空
     */
    public static String EP_EXCHANAGE_AMOUNT_NOT_NULL = "ep_exchanage_amount_not_null";
    /**
     * 收款人不能为空
     */
    public static String PAYEE_NOT_NULL = "payee_not_null";
    /**
     * 收款人不存在
     */
    public static String PAYEE_INVAIL = "payee_invail";
    /**
     * 收款人不能是自己
     */
    public static String PAYEE_NOT_SELF = "payee_not_self";
    /**
     * 收款人已被禁用
     */
    public static String PAYEE_DISABLE = "payee_disable";
    /**
     * 账户余额不足，请先兑换余额
     */
    public static String BALANCE_ERROR = "balance_error";

    /**
     * 请选择卖出金额
     */
    public static String SELECT_PAY_AMOUNT = "select_pay_amount";
    /**
     * 支付密码不正确
     */
    public static String PAYPWD_FAIL = "payPwd_fail";
    /**
     * 不能转给自己
     */
    public static String EXCHANGE_SELF_ERROR = "exchange_self_error";

    /**
     * 兑换失败
     */
    public static String EXCHANGE_FAIL = "exchange_fail";

    /**
     * 余额数量不足
     */
    public static String BALANCE_ERROR2 = "balance_error2";

    /**
     * 参数输入有误
     */
    public static String PARAM_ERROR = "param_error";

    /**
     * 非法访问，系统自动退出
     */
    public static String INVAILD_REQUEST = "invaild_request";
    /**
     * 过长时间没有操作，页面过期，请重新登录
     */
    public static String LOGIN_TIEMOUT = "login_tiemOut";
    /**
     * 请不要重复操作
     */
    public static String REPEAT_ACTION = "repeat_action";
    /**
     * 兑换成功
     */
    public static String EXCHANGE_SUCCESS = "exchange_success";
    /**
     * 兑换失败
     */
    public static String EXCHANGE_FAILURE = "exchange_failure";
    /**
     * 新增银行卡失败
     */
    public static String ADD_BANKCARD_FAIL = "add_bankCard_fail";

    /**
     * 旧登录密码错误
     */
    public static String OLD_LOGINPWD_ERROR = "old_loginPwd_error";

    /**
     * 确认打款失败
     * */
    public static String COMFIR_PAY_FAIL = "comfir_pay_fail";
    /**
     * 查询兑换记录失败
     * */
    public static String QUERY_EXCHANGE_RECORD_FAIL = "query_exchange_record_fail";
    /**
     * 请输入正确的手机号码
     * */
    public static final String PHONE_INVALID = "phone_invalid";

    /**
     * 兑换金额不能为空
     * */
    public static final String EXCHANGE_AMOUNT_NOT_NULL = "exchange_amount_not_null";
    /**
     * 兑换金额必须大于{0}
     */
    public static final String EXCHANGE_SCORE_COUNT_ERROR = "exchange_score_count_error";

    /**
     * 已优化安全性能，请更新到最新包
     * */
    public static final String UPGRADE_PROMPT="upgrade_prompt";


    /**
     *   用户交易密码有误
     * */
    public static final String ORDER_PAYPASS_INVILID="order_paypass_invilid";

    /**
     *   发布成功
     * */
    public static final String ORDER_PUBLISH_SUCCESS="order_publish_success";



    /**
     *   单笔交易金额太小
     * */
    public static final String ORDER_SINGLE_NOT_ENOUGH="order_single_not_enough";

    /**
     *达到最大未成交交易笔数
     * */
    public static final String REACHED_MAX_OUTSTANDING="reached_max_outstanding";


    /**
     * 请求失效
     */
    public static final String REQUEST_INVALID= "request_invalid";
    /**
     * 无效签名
     */
    public static final String INVALID_SIGN= "invalid_sign";

    /**
     * 昵称含有关键词，请重新输入
     * */
    public static final String HAS_KEY_WORDS="has_key_words";

    /***************************************菠萝芬*****************************************************/
    /**
     * 接点人不能为空
     * */
    public static final String CONTACT_PERSON_NOT_NULL="contact_person_not_null";
    /**
     * 用户激活次数不足
     * */
    public static final String ACTIVENO_NOT_ENOUGH="activeNo_not_enough";
    /**
     * 激活成功
     * */
    public static final String ACTIVE_SUCCESS="active_success";
    /**
     * 激活失败
     * */
    public static final String ACTIVE_FAILE="active_faile";
    /**
     * 该手机号不存在
     * */
    public static final String PHONE_NOT_EXIST="phone_not_exist";
    /**
     * 查询失败
     * */
    public static final String QUERY_ERROR="query_error";

    /**
     * 需要删除的用户ID不能为空
     */
    public static String DELUSERID_NOT_NULL = "deluserid_not_null";
    /**
     * 用户不是未激活状态，无法删除
     */
    public static String DELUSER_IS_NOT_ACTIVE = "deluser_is_not_active";
    /**
     * 删除用户失败
     */
    public static String DELUSER_FAILURE = "deluser_failure";
    /**
     * 候鸟积分不足
     */
    public static String BIRDSCORE_LESS = "birdScore_less";
    /**
     * 是否允许转让候鸟积分
     */
    public static String BIRDSCORE_ISALLOWED = "birdScore_isAllowed";
    /**
     * 图片验证码不能为空
     */
    public static String VERIFY_NOT_NULL_PICTRUE = "verify_not_null_pictrue";
    /**
     * 图片验证码已失效
     */
    public static String VERIFY_INVALID_PICTRUE = "verify_invalid_pictrue";
    /**
     * 图片验证码输入错误
     */
    public static String VARIFY_FAIL_PICTRUE = "varify_fail_pictrue";
    /**
     * 金额必须是100倍数
     */
    public static String AMOUNT_IS_100_MLUTIPLE = "amount_is_100_mlutiple";
    /**
     * EP余额不足
     */
    public static String EP_BALANCE_NOT_ENOUGH = "ep_balance_not_enough";
    /**
     *单笔提现金额必须满足范围
     */
    public static String EP_BALANCE_BETWEEN = "ep_balance_between";
    /**
     *每天最大提现笔数为
     */
    public static String NUMBER_PER_DAY = "maximum number_per_day";
    /**
     *银行卡id不能为空
     */
    public static String CARD_ID_NOT_NULL = "card_id_not_null";
    /**
     *查询不到用户银行卡信息
     */
    public static String CAN_NOT_FIND_CARDINFO = "can_not_find_cardinfo";
    /**
     *提现申请成功，等待审核打款
     */
    public static String WAIT_PAYING = "wait_paying";
    /**
     *提现申请失败
     */
    public static String APPLICATION_FAIL = "application_fail";
    /**
     *获取用户记录成功
     */
    public static String GET_RECORD_SUCCESS = "get_record_success";
    /**
     *获取用户记录失败
     */
    public static String GET_RECORD_FAILE = "get_record_faile";
    /**
     * 删除失败
     */
    public static String DEL_FAILURE = "del_failure";
    /**
     * 修改支付密码成功
     */
    public static String MODIFY_PAY_PASS_SUCCEE = "modify_pay_pass_success";
    /**
     * 修改支付密码失败
     */
    public static String MODIFY_PAY_PASS_FAIL = "modify_pay_pass_fail";
    /**
     * 修改登录密码成功
     */
    public static String MODIFY_LOGIN_PASS_SUCCESS = "modify_login_pass_success";
    /**
     * 修改登录密码失败
     */
    public static String MODIFY_LOGIN_PASS_FAIL = "modify_login_pass_fail";
    /**
     * 修改昵称成功
     */
    public static String MODIFY_NICKNAME_SUCCESS = "modify_nickname_success";
    /**
     * 修改昵称失败
     */
    public static String MODIFY_NICKNAME_SUCCESS_FAIL = "modify_nickname_success_fail";
    /**
     * 查询用户所有银行卡成功
     */
    public static String QUERY_USER_BANK_CARD_LIST_SUCCESS = "query_user_bank_card_list_success";
    /**
     * 查询用户所有银行卡失败
     */
    public static String QUERY_USER_BANK_CARD_LIST_FAIL = "query_user_bank_card_list_fail";
    /**
     * 获取用户默认银行卡成功
     */
    public static String QUERY_USER_DEFAULT_BANK_CARD_SUCCESS = "query_user_default_bank_card_success";
    /**
     * 获取用户默认银行卡失败
     */
    public static String QUERY_USER_DEFAULT_BANK_CARD_FAIL = "query_user_default_bank_card_fail";
    /**
     * 新增银行卡成功
     */
    public static String ADD_BANK_CARD_SUCCESS = "add_bank_card_success";
    /**
     * 新增银行卡失败
     */
    public static String ADD_BANK_CARD_FAIL = "add_bank_card_fail";
    /**
     * 修改银行卡成功
     */
    public static String UPDATE_BANK_CARD_SUCCESS = "update_bank_card_success";
    /**
     * 修改银行卡成功失败
     */
    public static String UPDATE_BANK_CARD_FAIL = "update_bank_card_fail";
    /**
     * 删除银行卡成功
     */
    public static String DELETE_BANK_CARD_SUCCESS = "delete_bank_card_success";
    /**
     * 删除银行卡失败
     */
    public static String DELETE_BANK_CARD_FAIL = "delete_bank_card_fail";
    /**
     * 设置默认银行卡成功
     */
    public static String SET_DEFAULT_CARD_SUCCESS = "set_default_card_success";
    /**
     * 设置默认银行卡失败
     */
    public static String SET_DEFAULT_CARD_FAIL = "set_default_card_fail";
    /**
     * 找回密码成功
     */
    public static String RETRIEVE_PASS_SUCCESS = "retrieve_pass_success";
    /**
     * 找回密码失败
     */
    public static String RETRIEVE_PASS_FAIL = "retrieve_pass_fail";



}
