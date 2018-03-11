package com.xlf.app.controller;

import com.xlf.common.enums.BankEnum;
import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.ParamTimeEnum;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.app.AppBillRecordVo;
import com.xlf.common.vo.app.DrawRecordVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppRacingBettingService;
import com.xlf.server.app.AppTimeBettingService;
import com.xlf.server.app.AppWithDrawService;
import com.xlf.server.common.CommonService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 报表
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Resource
    private CommonService commonService;
    @Resource
    private AppBillRecordService billRecordService;
    @Resource
    private LanguageUtil msgUtil;
    @Resource
    private AppWithDrawService appWithDrawService;
    @Resource
    private AppTimeBettingService appTimeBettingService;
    @Resource
    private AppRacingBettingService appRacingBettingService;

    /**
     * 盈亏报表
     */
    @GetMapping(value = "/consume")
    public RespBody findUserRecord(Integer paramTime) {
        RespBody respBody = new RespBody ();
        try {
            //根据用户id获取用户信息
            List<AppBillRecordVo> list = null;
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken ();
            List<Integer> busnessTypeListCosts = new ArrayList<> ();
            List<Integer> busnessTypeListIncome = new ArrayList<> ();
            List<Integer> busnessTypeListUndo = new ArrayList<> ();
            busnessTypeListCosts.add (BusnessTypeEnum.TIME_BETTING.getCode ());
            busnessTypeListCosts.add (BusnessTypeEnum.RACING_BETTING.getCode ());
            busnessTypeListIncome.add (BusnessTypeEnum.RACING_LOTTERY.getCode ());
            busnessTypeListIncome.add (BusnessTypeEnum.TIME_LOTTERY.getCode ());
            busnessTypeListUndo.add (BusnessTypeEnum.TIME_UNDO.getCode ());
            busnessTypeListUndo.add (BusnessTypeEnum.RACING_UNDO.getCode ());
            if (paramTime == null || paramTime == 0) {
                respBody.add (RespCodeEnum.ERROR.getCode (), AppMessage.PARAM_ERROR, "参数有误");
                return respBody;
            }

            Map<String,String> dateMap = null;
            if(ParamTimeEnum.CURRENTDAY.getCode().intValue() == paramTime){
                dateMap = DateTimeUtil.getCurrentDayTime();
            }else if(ParamTimeEnum.LASTWEEK.getCode().intValue() == paramTime){
                dateMap = DateTimeUtil.getLastWeekTime();
            }else if(ParamTimeEnum.CURRENTWEEK.getCode().intValue() == paramTime){
                dateMap = DateTimeUtil.getCurrentWeekTime();
            }else if(ParamTimeEnum.LASTMONTH.getCode().intValue() == paramTime){
                dateMap = DateTimeUtil.getLastMonthTime();
            }else if(ParamTimeEnum.CURRENTMONTH.getCode().intValue() == paramTime){
                dateMap = DateTimeUtil.getCurrentMonthTime();
            }

            if(dateMap == null){
                respBody.add (RespCodeEnum.ERROR.getCode (), AppMessage.PARAM_ERROR, "参数有误");
                return respBody;
            }

            String startTime = dateMap.get("startTime");
            String endTime = dateMap.get("endTime");

            Double costs = billRecordService.report (appUserPo.getId (), busnessTypeListCosts,startTime,endTime);
            Double income = billRecordService.report (appUserPo.getId (), busnessTypeListIncome,startTime,endTime);
            Double undo = billRecordService.report (appUserPo.getId (), busnessTypeListUndo,startTime,endTime);
            Integer costsCount = billRecordService.reportCount (appUserPo.getId (), busnessTypeListCosts,startTime,endTime);
            Map<String,BigDecimal> map=new HashMap<> ();
            if(costs == null){
                costs = 0d;
            }
            if(income == null){
                income = 0d;
            }
            if(undo == null){
                undo = 0d;
            }
            map.put ("costs",new BigDecimal ((costs-undo)).setScale (2,BigDecimal.ROUND_HALF_EVEN));
            map.put ("income",new BigDecimal (income).add (new BigDecimal ((costs+undo))).setScale (2,BigDecimal.ROUND_HALF_EVEN));
            map.put ("costsCount",new BigDecimal (costsCount));
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取用户报表成功", map);
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (),"获取用户记录失败");
            LogUtils.error ("获取用户记录失败！", ex);
        }
        return respBody;
    }



}
