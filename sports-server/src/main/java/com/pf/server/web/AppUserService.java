/* 
 * 文件名：UserService.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：lxl  
 * 创建时间：2017年6月12日
 * 版本号：v1.0
*/
package com.pf.server.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pf.common.exception.CommException;
import com.pf.common.po.AppUserPo;
import com.pf.common.resp.Paging;
import com.pf.common.vo.app.UserVo;
import com.pf.common.vo.pc.AppBillRecordVo1;
import com.pf.common.vo.pc.AppUserCoinAddress;
import com.pf.common.vo.pc.AppUserMessageVo;
import com.pf.common.vo.pc.HomeUser;
import com.pf.common.vo.pc.StatisticsVo;

/**
 * 用户业务层接口
 *
 * @author qsy
 * @version v1.0
 * @date 2017年6月12日
 */
public interface AppUserService {

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     * @throws Exception
     */
    public AppUserPo findUserById(String id) throws Exception;

    AppUserPo getUserByToken(String token) throws Exception;

    AppUserPo getUserById(String id) throws Exception;

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     * @throws Exception
     */
    public AppUserPo findUserByMobile(String mobile) throws Exception;

    /**
     * 用户登录
     *
     * @param appUserPo
     * @param loginPwd
     * @throws Exception
     */
    public String login(AppUserPo appUserPo, String loginPwd) throws CommException;

    int updateById(AppUserPo userPo, String userId) throws Exception;
    
    List<AppUserPo> getPoList(AppUserPo userPo,Paging paging);
    
    public Integer findPoListCount(AppUserPo po);
    
    List<AppUserPo> getLowerPoList(String parentId);
    
    StatisticsVo userStatistics()throws Exception;
    
    Integer getUserBytime(Date StarTime,Date endTime);
    
    
    /**
     * 
     * @param 用户查询
     * @return
     */
    public AppUserPo findUid(String uid);
    
    /**
	 * 首页用户统计
	 * @return
	 */
	
	public HomeUser homeSUM();
	
	/**
	 * 
	 * @param 今日注册、激活
	 * @return
	 */
	
	
	public  Integer SUMCount(AppUserPo po);
	
	
	public  Integer updateActiveNoCount(Integer activeNo,String id);


}
