package com.xlf.server.app.impl;

import com.xlf.common.enums.PerformanceTypeEnum;
import com.xlf.common.enums.RedisKeyEnum;
import com.xlf.common.enums.StateEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.ConfUtils;
import com.xlf.common.util.CryptUtils;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.ActiveVo;
import com.xlf.common.vo.app.UserInfoVo;
import com.xlf.common.vo.app.UserVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.common.CommonService;
import com.xlf.server.mapper.AppUserMapper;
import com.xlf.server.mapper.SysKeyWordsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public class AppUserServiceImpl implements AppUserService {
    @Resource
    private LanguageUtil msgUtil;
    @Resource
    private AppUserMapper appUserMapper;
    @Resource
    private ConfUtils confUtils;
    @Resource
    private SysKeyWordsMapper sysKeyWordsMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private AppBillRecordService billRecordService;
    @Resource
    private CommonService commonService;


    @Override
    public AppUserPo findUserById(String id) throws Exception {
        return appUserMapper.findUserById(id);
    }

    @Override
    public AppUserPo getUserByToken(String token) throws Exception {
        AppUserPo user = null;
        Object obj = redisService.getObj(token);
        if (obj != null && obj instanceof AppUserPo) {
            user = (AppUserPo) obj;
        }
        return user;
    }


    @Override
    public AppUserPo findUserByMobile(String mobile) throws Exception {
        return appUserMapper.findUserByMobile(mobile);
    }

    @Override
    public AppUserPo findUserByNickName(String nickName) throws Exception {
        return appUserMapper.findUserByNickName(nickName);
    }

    /**
     * 是否含有关键词
     *
     * @param nickName 昵称
     * @return >0 表示含有关键词汇
     */
    @Override
    public int findKeyWords(String nickName) {
        return sysKeyWordsMapper.findKeyWords(nickName);
    }

    /**
     * 注册用户业务
     *
     * @param userVo
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public Boolean add(UserVo userVo) throws Exception {

        AppUserPo appUserPo = new AppUserPo();
        //获取盐
        String pwdStal = ToolUtils.getUUID();
        String payStal = ToolUtils.getUUID();
        //设置登录密码
        String loginPw = CryptUtils.hmacSHA1Encrypt(userVo.getLoginPwd(), pwdStal);
        //设置支付密码
        String payPwd = CryptUtils.hmacSHA1Encrypt(userVo.getPayPwd(), payStal);

        appUserPo.setMobile(userVo.getMobile());
        appUserPo.setNickName(userVo.getNickName());
//        appUserPo.setParentId(userVo.getParentId());
        appUserPo.setLoginPwd(loginPw);
        appUserPo.setPayPwd(payPwd);
        appUserPo.setPwdStal(pwdStal);
        appUserPo.setPayStal(payStal);
        appUserPo.setId(ToolUtils.getUUID());
        appUserPo.setCreateTime(new Date());
        appUserPo.setState(StateEnum.NO_ACTIVE.getCode());

       int  count = appUserMapper.insert(appUserPo);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String login(AppUserPo appUserPo) throws Exception {

        //获取token
        String token = RedisKeyEnum.TOKEN_API.getKey() + ToolUtils.getUUID();
        // 登录成功,将用户信息存储到redis中
        String msg = redisService.putObj(token, appUserPo, confUtils.getSessionTimeout());
        if (!msg.equalsIgnoreCase("ok")) {
            // 缓存用户信息失败
            throw new CommException("设置token到redis失败");
        } else {
            //redis是否存在
            String token_key = redisService.getString(appUserPo.getId());
            if (!StringUtils.isEmpty(token_key)) {
                //删除token_key值
                redisService.del(token_key);
            }
            //用另外一个redis去保存token_key,保证用户登录只有一个token_key
            redisService.putString(appUserPo.getId(), token, confUtils.getSessionTimeout());
            //登录成功，修改登录时间
            AppUserPo model = new AppUserPo();
            model.setLoginTime(new Date());
            appUserMapper.updateById(model, appUserPo.getId());
            return token;
        }
    }

    @Override
    public int delUser(String userId) throws Exception {
        if (StringUtils.isEmpty(userId)){
            return 0;
        }
        int rows =0;
        if (rows<1){
            throw new CommException(msgUtil.getMsg(AppMessage.DELUSER_FAILURE, "删除用户失败"));
        }
        rows=appUserMapper.deleteByPrimaryKey(userId);
        if (rows<1){
            throw new CommException(msgUtil.getMsg(AppMessage.DELUSER_FAILURE, "删除用户失败"));
        }
        return rows;
    }

    /**
     * 根据用户id修改用户信息
     *
     * @param userPo
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public int updateById(AppUserPo userPo, String userId) throws Exception {
        return appUserMapper.updateById(userPo, userId);
    }




    @Override
    public UserInfoVo findUserByContactUserId(String userId) {
        return appUserMapper.findUserByContactUserId(userId);
    }



}
