package com.xlf.web.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.enums.WithDrawEnum;
import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.AppWithDrawPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.AppWithDrawVo;
import com.xlf.server.web.WebUserService;
import com.xlf.server.web.WebWithDrawService;
import com.xlf.server.web.WebBillRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/appWithDraw")
public class WebWithDrawController {
    @Resource
    private WebWithDrawService webWithDrawService;

    @Resource
    private WebUserService appUserService;
    @Resource
    private WebBillRecordService webBillRecordService;

    @GetMapping("/findAll")
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


            po.setState(20);
            webWithDrawService.update(po);
            /**
             * 修改用户冻结资金（冻结资金-提现金额）
             */
            AppUserPo appuser = appUserService.findUserById(po.getUserId());
            appUserService.updateById(appuser, appuser.getId());


            respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "操作成功");
            LogUtils.error("操作成功！", ex);
        }
        return respBody;
    }


    @PostMapping("/errorState")
    @SystemControllerLog(description = "驳回打款")
    public RespBody erroeState(@RequestBody AppWithDrawPo po) {
        RespBody respBody = new RespBody();
        try {


            po.setState(30);
            webWithDrawService.update(po);
            /**
             * 修改用户冻结资金（冻结资金 = 冻结资金-提现金额   用户ep余额 = 用户ep余额 +提现金额 ）
             */
            AppUserPo appuser = appUserService.findUserById(po.getUserId());
            appUserService.updateById(appuser, appuser.getId());


            AppBillRecordPo appBillRecordPo = new AppBillRecordPo();

            appBillRecordPo.setUserId(po.getUserId());
            appBillRecordPo.setBalance(po.getAmount());
            appBillRecordPo.setBusinessNumber(po.getId());

            appBillRecordPo.setBusnessType(22);
            appBillRecordPo.setRemark("提现驳回记录");
            webBillRecordService.add(appBillRecordPo);

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "操作成功");
            LogUtils.error("操作成功！", ex);
        }
        return respBody;
    }


}