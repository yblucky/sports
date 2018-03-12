/* 
 * 文件名：MainController.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月14日
 * 版本号：v1.0
*/
package com.xlf.web.controller;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.enums.RoleTypeEnum;
import com.xlf.common.enums.StateEnum;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.CryptUtils;
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.MyBeanUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.BankCardVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.common.vo.pc.WebUserVo;
import com.xlf.server.app.AppBankCardService;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
import com.xlf.server.web.WebUserService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 用户管理控制器
 *
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@RestController
@RequestMapping(value = "/webUser")
public class WebUserController {
    @Resource
    private WebUserService webAppUserService;
    @Resource
    private RedisService redisService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private CommonService commonService;
    @Resource
    private AppBillRecordService appBillRecordService;

    @Resource
    private AppBankCardService bankCardService;


    /**
     * 加载用户菜单
     *
     * @return 响应对象
     */
    @GetMapping("/userTab")
    public RespBody userTab(AppUserPo po, Paging paging) {
        RespBody respBody = new RespBody();
        try {
            //代理登录
            SysUserVo sysUser = commonService.checkWebToken();
            //只能查代理下面的会员
            if (RoleTypeEnum.AGENT.getCode().equals(sysUser.getRoleType())) {
                po.setParentId(sysUser.getId());
            }
            int total = webAppUserService.findPoListCount(po);
            List<WebUserVo> list = Collections.emptyList();
            if (total > 0) {
                list = webAppUserService.getPoList(po, paging);
            }
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载列表成功", list);
            paging.setTotalCount(total);
            respBody.setPage(paging);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "加载列表失败");
            LogUtils.error("加载列表失败！", ex);
        }
        return respBody;
    }

    @GetMapping("/findUid")
    public RespBody findUser(AppUserPo po, Paging paging) {
        RespBody respBody = new RespBody();
        try {
            AppUserPo appUserPo = webAppUserService.findUid(po.getUid().toString());
            if (appUserPo == null) {
                respBody.add(RespCodeEnum.SUCCESS.getCode(), "该用户不存在", appUserPo);
            } else {
                respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载列表成功", appUserPo);
            }
            paging.setTotalCount(webAppUserService.findPoListCount(po));
            respBody.setPage(paging);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "加载列表失败");
            LogUtils.error("加载列表失败！", ex);
        }
        return respBody;
    }


    /**
     * 禁用启用(删除)用户
     *
     * @return 响应对象
     */
    @PostMapping("/upUserState")
    public RespBody upUserState(@RequestBody AppUserPo po) {
        RespBody respBody = new RespBody();
        try {
            AppUserPo find = webAppUserService.findUserById(po.getId());
            if (null == find) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "用户错误");
            }
            if ((StateEnum.NORMAL.getCode().equals(find.getState()) || StateEnum.DISABLE.getCode().equals(find.getState()))) {
                int count = webAppUserService.updateById(po, po.getId());
                if (count > 0) {
                    if (StateEnum.DISABLE.getCode().equals(po.getState())) {
                        //取出用户Token  退出其登录
                        String token_key = redisService.getString(po.getId());
                        if (!org.apache.commons.lang3.StringUtils.isEmpty(token_key)) {
                            //删除token_key值
                            redisService.del(token_key);
                        }
                    }
                    respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改成功");
                } else {
                    respBody.add(RespCodeEnum.ERROR.getCode(), "修改失败");
                }
            } else {
                respBody.add(RespCodeEnum.ERROR.getCode(), "用户状态不正常");
            }
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "修改失败");
            LogUtils.error("修改用户状态失败", ex);
        }
        return respBody;
    }

    /**
     * 充值积分
     *
     * @return 响应对象
     */
    @PostMapping("/recharge")
    public RespBody recharge(@RequestBody AppUserPo po) {
        RespBody respBody = new RespBody();
        try {
            SysUserVo token = commonService.checkWebToken();
            if (token.getRoleType().intValue() == RoleTypeEnum.AGENT.getCode()) {
                if(BigDecimal.ZERO.compareTo(po.getBalance()) >= 0){
                    respBody.add(RespCodeEnum.ERROR.getCode(), "请输入正整数的数额");
                    return respBody;
                }
                if(po.getBalance().compareTo(token.getBalance())>1){
                    respBody.add(RespCodeEnum.ERROR.getCode(), "代理余额不足，请先充值");
                    return respBody;
                }
            }
            AppUserPo find = webAppUserService.findUid(po.getMobile());
            if (null == find) {
                find = webAppUserService.findUserByMobile(po.getMobile());
            }
            if (null == find) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "找不到用户");
                return respBody;
            }
            webAppUserService.recharge(find,po.getBalance(),token);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "充值成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "充值失败");
            LogUtils.error("充值失败", ex);
        }
        return respBody;
    }

    /**
     * 修改登陆密码
     *
     * @return 响应对象
     */
    @PostMapping("/upPwd")
    public RespBody upPwd(@RequestBody AppUserPo po) {
        RespBody respBody = new RespBody();
        try {
            SysUserVo token = commonService.checkWebToken();
            if (token.getRoleType().intValue() == RoleTypeEnum.AGENT.getCode()) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "不符合权限");
                return respBody;
            }
            AppUserPo find = webAppUserService.findUid(po.getMobile());
            if (null == find) {
                find = webAppUserService.findUserByMobile(po.getMobile());
            }
            if (null == find) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "找不到用户");
                return respBody;
            }
            //验证通过，更改登录密码
            AppUserPo model = new AppUserPo();
            //每次更新密码都要刷新盐
            String salt = ToolUtils.getUUID();
            //根据新盐加密登录密码
            String loginPw = CryptUtils.hmacSHA1Encrypt(po.getLoginPwd(), salt);
            model.setLoginPwd(loginPw);
            model.setPwdStal(salt);
            //调用通用的更新方法
            webAppUserService.updateById(model, find.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改密码成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "修改密码失败");
            LogUtils.error("修改密码失败", ex);
        }
        return respBody;
    }


    /**
     * 获取用户的上级
     */
    @GetMapping("/loadParent")
    public RespBody loadParent(String id) {
        RespBody respBody = new RespBody();
        try {
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载数据成功", sysUserService.findById(id));
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "加载列表失败");
            LogUtils.error("加载列表失败！", ex);
        }
        return respBody;
    }


    /**
     * 修改用户姓名
     *
     * @return 响应对象
     */
    @PostMapping("/upUserName")
    public RespBody upUserName(@RequestBody AppUserPo po) {
        RespBody respBody = new RespBody ();
        try {
            if (StringUtil.isEmpty (po.getName ())) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "姓名错误!");
            }
            AppUserPo find = webAppUserService.findUserById (po.getId ());
            if (null == find) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "用户错误");
            }
            if ((StateEnum.NORMAL.getCode ().equals (find.getState ()) || StateEnum.DISABLE.getCode ().equals (find.getState ()))) {
                AppUserPo upPo = new AppUserPo ();
                upPo.setName (po.getName ());
                int count = webAppUserService.updateById (upPo, po.getId ());
                if (count > 0) {
                    BankCardVo bankCardVo = new BankCardVo ();
                    bankCardVo.setName (po.getName ());
                    bankCardVo.setUserId (po.getId ());
                    bankCardService.update (bankCardVo);
                    respBody.add (RespCodeEnum.SUCCESS.getCode (), "修改成功");

                } else {
                    respBody.add (RespCodeEnum.ERROR.getCode (), "修改失败");
                }
            } else {
                respBody.add (RespCodeEnum.ERROR.getCode (), "用户状态不正常");
            }
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "修改失败");
            LogUtils.error ("修改用户状态失败", ex);
        }
        return respBody;
    }


}

