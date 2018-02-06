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
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
import com.xlf.server.web.WebUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

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


    /**
     * 加载用户菜单
     *
     * @return 响应对象
     */
    @GetMapping("/userTab")
    public RespBody userTab(AppUserPo po, Paging paging) {
        RespBody respBody = new RespBody();
        try {
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载列表成功", webAppUserService.getPoList(po, paging));
            paging.setTotalCount(webAppUserService.findPoListCount(po));
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
            if(token.getRoleType().intValue() == RoleTypeEnum.AGENT.getCode()){
                respBody.add(RespCodeEnum.ERROR.getCode(), "不符合权限");
                return respBody;
            }
            AppUserPo find = webAppUserService.findUid(po.getUid()+"");
            if (null == find) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "找不到用户");
                return respBody;
            }
            if(BigDecimal.ZERO.compareTo(po.getBalance()) >= 0){
                respBody.add(RespCodeEnum.ERROR.getCode(), "请输入正整数的数额");
                return respBody;
            }
            webAppUserService.updateBalance(find.getId(),po.getBalance());
            //流水记录
            appBillRecordService.saveBillRecord(ToolUtils.getOrderNo(),find.getId(), BusnessTypeEnum.BACK_RECHARGE.getCode()
            ,po.getBalance(),find.getBalance(),find.getBalance().add(po.getBalance()),"后台充值","");
            respBody.add(RespCodeEnum.SUCCESS.getCode(),"充值成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "充值失败");
            LogUtils.error("充值失败", ex);
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



}

