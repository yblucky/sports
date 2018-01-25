package com.xlf.web.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.WebBillRecordVo;
import com.xlf.common.vo.pc.RechangeVo;
import com.xlf.server.web.WebUserService;
import com.xlf.server.web.WebBillRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 充值
 *         zyc
 *         2018-01-13
 */


@RestController
@RequestMapping("/rechange")
public class RechangeController {

    @Resource
    private WebBillRecordService webBillRecordService;

    @Resource
    private WebUserService appUserService;


    /**
     * 充值记录
     *
     * @param vo
     * @param paging
     * @return
     */
    @GetMapping("/findAll")
    public RespBody findAll(WebBillRecordVo vo, Paging paging) {
        RespBody respBody = new RespBody();
        try {
            vo.setBusnessType(11);
            //保存返回数据
            List<WebBillRecordVo> list = webBillRecordService.findAll(vo, paging);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "查询记录数据成功", list);
            //保存分页对象
            paging.setTotalCount(webBillRecordService.findCount(vo));
            respBody.setPage(paging);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "查询记录数据失败");
            LogUtils.error("查找所有数据失败！", ex);
        }
        return respBody;
    }


    @PostMapping("/add")
    @SystemControllerLog(description = "充值")
    public RespBody erroeState(@RequestBody RechangeVo vo) {
        RespBody respBody = new RespBody();
        try {


            //appWithDrawService.update(po);
            /**
             * 修改用户冻结资金（冻结资金 = 冻结资金-提现金额   用户ep余额 = 用户ep余额 +提现金额 ）
             */
            AppUserPo appuser = appUserService.findUid(vo.getUid().toString());

            if (appuser == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "用户查询不到");
            } else {
                appuser.setBalance(appuser.getBalance().add(vo.getBalance()));
                appUserService.updateById(appuser, appuser.getId());


                AppBillRecordPo appBillRecordPo = new AppBillRecordPo();

                appBillRecordPo.setUserId(appuser.getId());
                appBillRecordPo.setBalance(vo.getBalance());
                appBillRecordPo.setBusinessNumber(appuser.getId());
                appBillRecordPo.setBeforeBalance(new BigDecimal("0"));
                appBillRecordPo.setAfterBalance(appuser.getBalance());

                appBillRecordPo.setBusnessType(11);
                appBillRecordPo.setRemark("给用户" + appuser.getUid() + "充值" + vo.getBalance() + "ep");
                webBillRecordService.add(appBillRecordPo);

                respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
            }
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "操作成功");
            LogUtils.error("操作成功！", ex);
        }
        return respBody;
    }


}
