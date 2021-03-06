package com.xlf.server.common;

import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysUserPo;
import com.xlf.common.vo.pc.SysUserVo;

import java.util.List;
import java.util.Map;

/**
 * 通用业务层接口
 *
 * @author qsy
 * @version v1.0
 * @date 2017年7月18日
 */
public interface CommonService {

    /**
     * 获取手机验证码
     *
     * @param mobile 手机号
     * @throws Exception
     */
    public void sendSms(String mobile, String areaCode) throws Exception;

    /**
     * 获取redis中的验证码
     *
     * @param mobile 手机号
     * @return 验证码
     */
    public String findCode(String mobile);

    /**
     * 获取APP登录的用户信息
     *
     * @return APP用户信息
     * @throws Exception
     */
    AppUserPo findAppUser() throws Exception;

    /**
     * 根据token获取用户信息
     *
     * @return 验证码
     */
    public AppUserPo checkToken() throws Exception;

    public SysUserVo checkWebToken() throws Exception;

    /**
     * 查询系统参数
     * @param paraName 参数key
     * @return 参数value
     */
    public String findParameter(String paraName);

    Integer  updateParameterByName(String paraName,String value);

    /**
     * 获取全球国家代码
     * @throws Exception
     */
    public List<AppRacingBettingPo> findCountryCode() throws Exception;

    public String getToken();

    public String getSign();

    public List getSignKey();

    public List getSignKeyByGet();

    public List getSignValueByGet(Map<String, String> params, List<String> keys);

    public String getTimeStamp();

    public List getSignValue(Object o, List<String> keys);

    public Boolean checkSign(String sign, String token, String key, List<String> parameNames, List<String> parameValues, String spilt, String timeStamp);

    public Boolean checkSign(Object o);

    public Boolean checkSignByGet(Map<String, String> params);

}
