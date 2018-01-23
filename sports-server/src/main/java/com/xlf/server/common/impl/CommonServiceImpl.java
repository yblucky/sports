package com.xlf.server.common.impl;

import com.xlf.common.enums.LanguageEnum;
import com.xlf.common.enums.StateEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppCountryCode;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.service.RedisService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.common.CommonService;
import com.xlf.server.mapper.AppCountryCodeMapper;
import com.xlf.server.web.ParameterService;
import com.xlf.common.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用业务层实现类
 *
 * @author qsy
 * @version v1.0
 * @date 2017年7月18日
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Resource
    private HttpServletRequest request;
    @Resource
    private AppUserService appUserService;
    @Resource
    private RedisService redisService;
    @Resource
    private ParameterService parameterService;
    @Resource
    private NewSmsUtil newSmsUtil;
    @Resource
    private AppCountryCodeMapper appCountryCodeMapper;
    @Resource
    private LanguageUtil languageUtil;

    @Override
    public void sendSms(String mobile, String areaCode) throws Exception {
        //默认国内短信
        String language = LanguageEnum.ZH_CN.getCode();
        //验证码短信模板
        String verifySms = this.findParameter("verifyCodeSms_zh_cn");
        //国内手机不用加区号
        String phone = mobile;
        //国际号码
        if (!"86".equals(areaCode)) {
            language = LanguageEnum.EN_US.getCode();
            verifySms = this.findParameter("verifyCodeSms_zh_en");
            //国际号码规则：前缀00+区号+号码
            phone = "00" + areaCode + mobile;
            phone = phone.trim();
        }
        //生成手机验证码
        String code = ToolUtils.getCode();
        //将验证码保存到redis中
        redisService.putString(mobile, code, newSmsUtil.getOutTime());
        //调用第三方接口发送短信
//        smsUtil.sendSms(mobile, code);
        newSmsUtil.sendSms(language, phone, code, verifySms);
    }

    @Override
    public String findCode(String mobile) {
        return redisService.getString(mobile);
    }


    /**
     * 根据token读取APP用户信息
     */
    @Override
    public AppUserPo findAppUser() throws Exception {
        String token = request.getHeader("token");
        if (null == token) {
            token = request.getParameter("token");
        }
        LogUtils.info("获取Token:" + token);
        if(null == token){
            LogUtils.error("登录已失效，，，，，"+request.getRequestURI());
        }
        //读取APP用户信息
        return appUserService.getUserByToken(token);
    }

    /**
     * 校验token的用户信息
     */
    @Override
    public AppUserPo checkToken() throws Exception {
        AppUserPo userPo = this.findAppUser();
        if (userPo == null) {
            throw new CommException("登录已失效:"+request.getRequestURI());
        }
        //重新获取用户最新信息
        userPo = appUserService.findUserById(userPo.getId());
        if (userPo == null) {
            throw new CommException(languageUtil.getMsg(AppMessage.USER_INVALID,"用户不存在"));
        }
        if (StateEnum.DISABLE.getCode().equals(userPo.getState())) {
            //清除token缓存，强制用户下线
            String token_key = redisService.getString(userPo.getId());
            if(!StringUtils.isEmpty(token_key)){
                //删除token_key值
                redisService.del(token_key);
            }
            throw new CommException(languageUtil.getMsg(AppMessage.USER_DISABLE,"用户已被禁用"));
        }
        return userPo;
    }

    @Override
    public String findParameter(String paraName) {
        return parameterService.findParameter(paraName);
    }

    @Override
    public List<AppCountryCode> findCountryCode() throws Exception {
        return appCountryCodeMapper.selectAll();
    }


    @Override
    public List getSignKey() {
        List<String> list = new ArrayList<>();
        try {
            String signKey = request.getHeader("signKey");
            if (StringUtils.isEmpty(signKey)) {
                signKey = request.getParameter("signKey");
            }
            if (StringUtils.isNotEmpty(signKey)) {
                String[] strArr = signKey.split("/");
                for (String str : strArr) {
                    list.add(str);
                }
            }
        } catch (Exception ex) {
            LogUtils.info("获取请求signKey失败");
            return list;
        }
        return list;
    }

    //针对get请求,获取参数
    @Override
    public List getSignKeyByGet() {
        List<String> list = new ArrayList<>();
        try {
            String spmsg = "\\@\\#\\$qwerty\\&\\*";
            String signKey = request.getHeader("signKey");
            if (StringUtils.isEmpty(signKey)) {
                signKey = request.getParameter("signKey");
            }
            if (StringUtils.isNotEmpty(signKey)) {
                String[] strArr = signKey.split("/");
                for (String str : strArr) {
                    String[] str1 = str.split(spmsg);
                    list.add(str1[0]);
                }
            }
        } catch (Exception ex) {
            LogUtils.info("获取请求signKey失败");
            return list;
        }
        return list;
    }

    //针对get请求，获取参数值
    @Override
    public List getSignValueByGet(Map<String,String> params, List<String> keys) {
        return MyBeanUtils.getFiledValuesByGet(params, keys);
    }

    @Override
    public String getSign() {
        String sign = request.getHeader("sign");
        if (StringUtils.isEmpty(sign)) {
            sign = request.getParameter("sign");
        }
        return sign;
    }

    @Override
    public String getTimeStamp() {
        String timestamp = request.getHeader("timestamp");
        if (StringUtils.isEmpty(timestamp)) {
            timestamp = request.getParameter("timestamp");
        }
        return timestamp;
    }

    @Override
    public List getSignValue(Object o, List<String> keys) {
        return MyBeanUtils.getFiledValues(o, keys);
    }

    @Override
    public String getToken() {
        String token = request.getHeader("token");
        return token;
    }

    @Override
    public Boolean checkSign(String sign, String token, String key, List<String> parameNames, List<String> parameValues, String spilt, String timeStamp) {
        String redisSign = redisService.getString(sign);
        if (StringUtils.isEmpty(redisSign)) {
            return false;
        }
        if (StringUtils.isEmpty(timeStamp)) {
            return false;
        }
        // StringUtils.isEmpty(token)
        if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(key)) {
            return false;
        }
        if (CollectionUtils.isEmpty(parameNames) || CollectionUtils.isEmpty(parameValues)) {
            return false;
        }
        if (parameNames.size() != parameValues.size()) {
            return false;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < parameNames.size(); i++) {
            stringBuffer.append(parameNames.get(i));
            stringBuffer.append(spilt);
            stringBuffer.append(parameValues.get(i) + "/");
        }
        String waitSignStr = stringBuffer.substring(0,stringBuffer.length()-1);
        waitSignStr = waitSignStr.toString() + key + token+timeStamp;
        LogUtils.info("前端待签名字符串 "+sign);
        LogUtils.info("后台待签名字符串 "+waitSignStr);
        try {
            String serverSign = CryptUtils.md5Digest(waitSignStr).toLowerCase();
            if (serverSign.equals(sign)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //针对post请求
    @Override
    public Boolean checkSign(Object o) {
        List<String> keys = this.getSignKey();
        List<String> values = this.getSignValue(o, keys);
        String sign = this.getSign();
        if(StringUtils.isEmpty(sign)){
            return false;
        }
        String timeStamp = this.getTimeStamp();
        String token = this.getToken();
        return checkSign(sign, token, "@#$qwerty&*", keys, values, "@#$qwerty&*", timeStamp);
    }

    //针对get请求,进行验签
    @Override
    public Boolean checkSignByGet(Map<String,String> params) {
        List<String> keys = this.getSignKeyByGet();
        List<String> values = this.getSignValueByGet(params, keys);
        String sign = this.getSign();
        if(StringUtils.isEmpty(sign)){
            return false;
        }
        String timeStamp = this.getTimeStamp();
        String token = this.getToken();
        if(StringUtils.isEmpty(token)){
            token = "";
        }
        return checkSign(sign, token, "@#$qwerty&*", keys, values, "@#$qwerty&*",timeStamp);
    }

    public static void main(String[] args) throws Exception {
        String msg = "mobile@#$qwerty&*13680334541";
        System.out.println(msg.split("\\@\\#\\$qwerty\\&\\*")[0]);
        System.out.println(CryptUtils.md5Digest("pageNumber@#$qwerty&*1/pageSize@#$qwerty&*10/bankName@#$qwerty&*/amount@#$qwerty&*1000.0@#$qwerty&*token_api_8d8ee56b1d6845699e261254c0669b481512006995323").toLowerCase());
        System.out.println("31d16a9237acf4c02316c10957900a3b");
        System.out.println("000000000000");
        System.out.println(CryptUtils.md5Digest("mobile@#$qwerty&*18826214583/loginPwd@#$qwerty&*a123456/imgKey@#$qwerty&*4a34722038f743028a13457b94d98628/imgKeyValue@#$qwerty&*0498@#$qwerty&*1516009847734").toLowerCase());
    }

}
