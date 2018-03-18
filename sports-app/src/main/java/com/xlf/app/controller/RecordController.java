package com.xlf.app.controller;

import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用   户资产相关
 */
@RestController
@RequestMapping("/record")
public class RecordController {
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
     * 用户流水记录
     */
    @GetMapping(value = "/list")
    public RespBody findUserRecord(HttpServletRequest request,String busnessType, Paging paging,String start,String end) {
        RespBody respBody = new RespBody ();
        try {
            //根据用户id获取用户信息
            List<AppBillRecordVo> list = null;
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken ();
            if (StringUtils.isEmpty (busnessType)) {
                paging.setTotalCount (0);
                respBody.add (RespCodeEnum.ERROR.getCode (), AppMessage.PARAM_ERROR, "参数不合法");
            }
            String[] busnessTypes = busnessType.split(",");
            if (ArrayUtils.isEmpty (busnessTypes)) {
                paging.setTotalCount (0);
                respBody.add (RespCodeEnum.ERROR.getCode (), AppMessage.PARAM_ERROR, "参数不合法");
            }
            List<Integer> busnessTypeList = new ArrayList<> ();
            for (String b : busnessTypes) {
                busnessTypeList.add (Integer.valueOf (b));
            }
            //获取总记录数量
            int total = billRecordService.billRecordListTotal (appUserPo.getId (), busnessTypeList);
            if (total > 0) {
                list = billRecordService.findBillRecordList (appUserPo.getId (), busnessTypeList, paging);
            }
            //返回前端总记录
            paging.setTotalCount (total);
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取用户记录成功", paging, list);
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (),"获取用户记录失败");
            LogUtils.error ("获取用户记录失败！", ex);
        }
        return respBody;
    }


    /**
     * 用户时时彩投注详细流水记录
     */
    @GetMapping(value = "/timeBettingRecord")
    public RespBody timeBettingRecord(HttpServletRequest request, Paging paging, String businessNumber) {
        RespBody respBody = new RespBody ();
        try {
            //根据用户id获取用户信息
            List<AppTimeBettingPo> list = null;
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken ();
            if (StringUtils.isEmpty (businessNumber)) {
                paging.setTotalCount (0);
                respBody.add (RespCodeEnum.ERROR.getCode (), AppMessage.PARAM_ERROR, "参数不合法");
            }

            //获取总记录数量
            int total = appTimeBettingService.recordListTotal (appUserPo.getId (), businessNumber);
            if (total > 0) {
                list = appTimeBettingService.findRecordList (appUserPo.getId (), businessNumber, paging);
            }
            //返回前端总记录
            paging.setTotalCount (total);
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取用户记录成功", paging, list);
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取用户记录失败");
            LogUtils.error ("获取用户记录失败！", ex);
        }
        return respBody;
    }

    /**
     * 用户北京赛车投注详细流水记录
     */
    @GetMapping(value = "/racingBettingRecord")
    public RespBody racingBettingRecord(HttpServletRequest request, Paging paging, String businessNumber) {
        RespBody respBody = new RespBody ();
        try {
            //根据用户id获取用户信息
            List<AppRacingBettingPo> list = null;
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken ();
            if (StringUtils.isEmpty (businessNumber)) {
                paging.setTotalCount (0);
                respBody.add (RespCodeEnum.ERROR.getCode (), AppMessage.PARAM_ERROR, "参数不合法");
            }

            //获取总记录数量
            int total = appRacingBettingService.recordListTotal (appUserPo.getId (), businessNumber);
            if (total > 0) {
                list = appRacingBettingService.findRecordList (appUserPo.getId (), businessNumber, paging);
            }
            //返回前端总记录
            paging.setTotalCount (total);
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取用户记录成功", paging, list);
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取用户记录失败");
            LogUtils.error ("获取用户记录失败！", ex);
        }
        return respBody;
    }


    /**
     * 用户流水记录
     */
    @GetMapping(value = "/withdrawRecord")
    public RespBody withdrawRecord(HttpServletRequest request, Paging paging) {
        RespBody respBody = new RespBody ();
        try {
            //根据用户id获取用户信息
            List<DrawRecordVo> list = Collections.emptyList ();
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken ();
            //获取总记录数量
            int total = appWithDrawService.drawRecordTotal (appUserPo.getId ());
            if (total > 0) {
                list = appWithDrawService.drawRecordList (appUserPo.getId (), paging);
            }
            //返回前端总记录
            paging.setTotalCount (total);
            respBody.add (RespCodeEnum.SUCCESS.getCode (), msgUtil.getMsg (AppMessage.GET_RECORD_SUCCESS, "获取用户记录成功"), paging, list);
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), msgUtil.getMsg (AppMessage.GET_RECORD_FAILE, "获取用户记录失败"));
            LogUtils.error ("获取用户记录失败！", ex);
        }
        return respBody;
    }


}
