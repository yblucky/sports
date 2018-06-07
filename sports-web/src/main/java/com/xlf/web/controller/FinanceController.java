/* 
 * 文件名：MainController.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月14日
 * 版本号：v1.0
*/
package com.xlf.web.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.DateTypeEnum;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.enums.RoleTypeEnum;
import com.xlf.common.enums.WithDrawEnum;
import com.xlf.common.po.AppWithDrawPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.DateUtils;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.AppWithDrawVo;
import com.xlf.common.vo.pc.LotteryVo;
import com.xlf.common.vo.pc.RevenueVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppRacingBettingService;
import com.xlf.server.app.AppTimeLotteryService;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
import com.xlf.server.web.WebWithDrawService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 财务控制器
 *
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@RestController
@RequestMapping(value = "/finance")
public class FinanceController {
    @Resource
    private AppRacingBettingService appRacingBettingService;
    @Resource
    private CommonService commonService;
    @Resource
    private AppBillRecordService appBillRecordService;
    @Resource
    private WebWithDrawService webWithDrawService;
    @Resource
    private SysUserService sysUserService;

    /**
     * 注单列表
     *
     * @return 响应对象
     */
    @GetMapping("/findAll")
    public RespBody findAll(LotteryVo vo, Paging paging) {
        RespBody respBody = new RespBody();
        try {
            SysUserVo sysUser = commonService.checkWebToken();
            if (sysUser != null && RoleTypeEnum.AGENT.getCode().equals(sysUser.getRoleType())) {
                vo.setParentId(sysUser.getId());
            }
            String state = vo.getState();
            int total = 0;
            List<LotteryVo> list = Collections.emptyList();
            //查看总的投注
            if (StringUtils.isEmpty(state) || "10".equals(state)) {
                total = appBillRecordService.findBetRecordCount(vo);
                if (total > 0) {
                    list = appBillRecordService.findBetRecord(vo, paging);
                }
            } else {
                total = appRacingBettingService.findAllCount(vo);
                if (total > 0) {
                    list = appRacingBettingService.findAll(vo, paging);
                }
            }
            paging.setTotalCount(total);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载注单信息成功", paging, list);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "加载注单失败");
            LogUtils.error("加载注单失败！", ex);
        }
        return respBody;
    }

    /**
     * 营收列表
     *
     * @return 响应对象
     */
    @GetMapping("/revenueList")
    public RespBody revenueList(RevenueVo vo, Paging paging) {
        RespBody respBody = new RespBody();
        try {
            SysUserVo userVo = commonService.checkWebToken();
            //时间差
            if (DateTypeEnum.YESTERDAY.getCode().equals(vo.getDateType())) {
                vo.setStartDiff(-1);
                vo.setEndDiff(-1);
            } else if (DateTypeEnum.LASTWEEK.getCode().equals(vo.getDateType())) {
                int weeks = DateUtils.getWeekOfDate(new Date());
                vo.setStartDiff((weeks + 7) * -1);
                vo.setEndDiff(-1 * weeks);
            } else if (DateTypeEnum.THISWEEK.getCode().equals(vo.getDateType())) {
                int weeks = DateUtils.getWeekOfDate(new Date());
                vo.setStartDiff(weeks * -1);
                vo.setEndDiff(7);
            } else if (DateTypeEnum.LASTMONTH.getCode().equals(vo.getDateType())) {
                //取得系统当前时间
                Calendar cal = Calendar.getInstance();
                //取得系统当前时间所在月第一天时间对象
                cal.set(Calendar.DAY_OF_MONTH, 1);
                //日期减一,取得上月最后一天时间对象
                cal.add(Calendar.DAY_OF_MONTH, -1);
                //输出上月最后一天日期
                int lastDay = cal.get(Calendar.DAY_OF_MONTH);
                int today = new Date().getDate();
                vo.setStartDiff((lastDay + today) * -1);
                vo.setEndDiff(today * -1);
            } else if (DateTypeEnum.THISMONTH.getCode().equals(vo.getDateType())) {
                int today = new Date().getDate();
                vo.setStartDiff(today * -1);
                vo.setEndDiff(0);
            } else if (vo.getStartTime() != null || vo.getEndTime() != null) {
                vo.setStartDiff(-399);
                vo.setEndDiff(-399);
            } else {
                vo.setStartDiff(0);
                vo.setEndDiff(0);
            }
            SysUserVo sysUserVo = new SysUserVo();
            //代理登录只能查看自己的营收记录
            if (userVo.getRoleType().intValue() == RoleTypeEnum.AGENT.getCode()) {
                vo.setUserId(userVo.getId());
                sysUserVo.setId(userVo.getId());
            }
            long counts = 0;
            if (StringUtils.isEmpty(vo.getFindDetail())) {
                counts = sysUserService.findCount(sysUserVo);
            } else {
                counts = appBillRecordService.revenueCount(vo);
            }
            List<RevenueVo> list = null;
            if (counts > 0) {
                list = appBillRecordService.revenueList(vo, paging);
            }
            paging.setTotalCount(counts);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载注单信息成功", paging, list);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "加载注单失败");
            LogUtils.error("加载注单失败！", ex);
        }
        return respBody;
    }


    @GetMapping("/findAllWithDraw")
    public RespBody findAllRerocd(AppWithDrawVo vo, Paging paging) {
        RespBody respBody = new RespBody();
        try {
            //保存返回数据
            List<AppWithDrawVo> list = webWithDrawService.findAll(vo, paging);
            for (AppWithDrawVo vo1 : list) {
                vo1.setTypeName(WithDrawEnum.getName(vo1.getState()));
            }
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有提现记录数据成功", list);
            //保存分页对象
            paging.setTotalCount(webWithDrawService.findCount(vo));
            respBody.setPage(paging);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有提现记录数据失败");
            LogUtils.error("查找所有提现记录信息数据失败！", ex);
        }
        return respBody;
    }


    @PostMapping("/successState")
    @SystemControllerLog(description = "确认打款")
    public RespBody successState(@RequestBody AppWithDrawPo po) {
        RespBody respBody = new RespBody();
        try {
            SysUserVo token = commonService.checkWebToken();
            if (token.getRoleType().intValue() == RoleTypeEnum.AGENT.getCode()) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "不符合权限");
                return respBody;
            }
            //确认打款
            webWithDrawService.successState(po.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
            LogUtils.error("操作失败！", ex);
        }
        return respBody;
    }


    @PostMapping("/errorState")
    @SystemControllerLog(description = "驳回打款")
    public RespBody erroeState(@RequestBody AppWithDrawPo po) {
        RespBody respBody = new RespBody();
        try {
            SysUserVo token = commonService.checkWebToken();
            if (token.getRoleType().intValue() == RoleTypeEnum.AGENT.getCode()) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "不符合权限");
                return respBody;
            }
            //驳回提现
            webWithDrawService.erroeState(po.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "操作成功");
            LogUtils.error("操作成功！", ex);
        }
        return respBody;
    }
}




