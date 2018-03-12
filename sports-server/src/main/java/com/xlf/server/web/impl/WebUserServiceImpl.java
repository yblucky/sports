package com.xlf.server.web.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.xlf.common.vo.pc.WebStatisticsVo;
import com.xlf.common.vo.pc.WebUserVo;
import org.springframework.stereotype.Service;

import com.xlf.common.enums.RedisKeyEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.ConfUtils;
import com.xlf.common.util.CryptUtils;
import com.xlf.common.vo.pc.HomeUser;
import com.xlf.common.vo.pc.StatisticsVo;
import com.xlf.server.mapper.AppUserMapper;
import com.xlf.server.web.WebUserService;

/**
 * 用户业务层实现类
 *
 * @author qsy
 * @version v1.0
 * @date 2017年6月12日
 */
@Service("webAppUserService")
public class WebUserServiceImpl implements WebUserService {
    @Resource
    private AppUserMapper appUserMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private ConfUtils confUtils;

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
    public AppUserPo getUserById(String id) throws Exception {
        return appUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public AppUserPo findUserByMobile(String mobile) throws Exception {

        return appUserMapper.findUserByMobile(mobile);
    }
    
    @Override
    public String login(AppUserPo appUserPo, String loginPwd) throws CommException {

        //获取token
        String token = RedisKeyEnum.TOKEN_API.getKey()+appUserPo.getId();
        // 用户有效，对输入密码进行加密
        loginPwd = CryptUtils.hmacSHA1Encrypt(loginPwd, appUserPo.getPwdStal());
        // 验证密码是否正确
        if (loginPwd.equals(appUserPo.getLoginPwd())) {
            //将对象转换成序列化对象
            // 登录成功,将用户信息存储到redis中
            String msg = redisService.putObj(token, appUserPo, confUtils.getSessionTimeout());
            if (!msg.equalsIgnoreCase("ok")) {
                // 缓存用户信息失败
                throw new CommException("设置token到redis失败");
            } else {
                //登录成功，修改登录时间
                appUserPo.setLoginTime(new Date());
                appUserMapper.updateByPrimaryKey(appUserPo);
                return token;
            }
        } else {
            throw new CommException("登录密码错误");
        }
    }

    /**
     * 修改登录密码
     *
     * @param userPo 要更改的PO类
     * @param userId 要更改的用户ID
     * @return int类型
     */
    @Override
    public int updateById(AppUserPo userPo, String userId) throws Exception {
        return appUserMapper.updateById(userPo, userId);
    }
    
    @Override
	public List<AppUserPo> getLowerPoList(String  parentId) {
		return appUserMapper.getLowerPoList(parentId);
	}
    
	@Override
	public List<WebUserVo> getPoList(AppUserPo userPo, Paging paging) {
		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		return appUserMapper.getPoList(userPo, startRow,pageSize);
	}

	@Override
	public StatisticsVo userStatistics() throws Exception {
		return appUserMapper.userStatistics();
	}

	@Override
	public Integer getUserBytime(Date StarTime, Date endTime) {
		return appUserMapper.getUserBytime(StarTime, endTime);
	}

	@Override
	public Integer findPoListCount(AppUserPo po) {
		// TODO Auto-generated method stub
		return appUserMapper.findPoListCount(po);
	}

	@Override
	public AppUserPo findUid(String uid) {
		// TODO Auto-generated method stub
		return appUserMapper.findUid(uid);
	}

	@Override
	public WebStatisticsVo homeSUM() {
		// TODO Auto-generated method stub
		return appUserMapper.homeSUM();
	}

	@Override
	public Integer SUMCount() {
		// TODO Auto-generated method stub
		return appUserMapper.SUMCount();
	}

	@Override
	public Integer updateActiveNoCount(Integer activeNo, String id) {
		// TODO Auto-generated method stub
		return appUserMapper.updateActiveNoCount(activeNo, id);
	}

    @Override
    public int updateBalance(String userId, BigDecimal balance) {
        return appUserMapper.updateBalanceById(userId,balance);
    }
}
