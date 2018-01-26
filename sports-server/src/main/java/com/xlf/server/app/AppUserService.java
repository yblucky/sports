package com.xlf.server.app;

import com.xlf.common.po.AppUserPo;
import com.xlf.common.vo.app.UserVo;

/**
 * 用户相关业务
 * Created by Administrator on 2018/1/4 0004.
 */
public interface AppUserService {


    /**
     * 根据用户id查询用户
     * @param id
     * @return
     * @throws Exception
     */
    public AppUserPo findUserById(String id) throws Exception;

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     * @throws Exception
     */
    public AppUserPo getUserByToken(String token) throws Exception;

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     * @throws Exception
     */
    public AppUserPo findUserByMobile(String mobile) throws Exception;

    /**
     * 根据昵称查询用户
     *
     * @param nickName
     * @return
     * @throws Exception
     */
    public AppUserPo findUserByNickName(String nickName) throws Exception;

    /**
     * 是否是关键词汇
     * */
    int findKeyWords(String nickName);

    /**
     * 注册用户
     *
     * @param userVo
     * @throws Exception
     */
    public Boolean add(UserVo userVo) throws Exception;

    /**
     * 用户登录
     *
     * @param appUserPo
     * @throws Exception
     */
    public String login(AppUserPo appUserPo) throws Exception;


    /**
     * 删除用户
     */
    public int delUser(String userId) throws Exception;



    public AppUserPo findUserByParentId(String parentId) throws Exception;

    /**
     * 根据用户id修改用户信息
     * @param userPo
     * @param userId
     * @return
     * @throws Exception
     */
    public int updateById(AppUserPo userPo, String userId) throws Exception;


}
