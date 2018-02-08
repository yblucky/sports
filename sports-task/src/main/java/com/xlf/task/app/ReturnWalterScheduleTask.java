package com.xlf.task.app;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppTimeLotteryService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.app.SysAgentSettingService;
import com.xlf.server.web.SysUserService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ReturnWalterScheduleTask extends BaseScheduleTask {
    @Resource
    private AppTimeLotteryService appTimeLotteryService;
    @Resource
    private AppUserService appUserService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysAgentSettingService sysAgentSettingService;


    @Override
    protected void doSpecificTask() {
        try {
            Integer count = appUserService.countWaitingReturnWaterUser ();
            if (count == 0) {
                return;
            }
            List<AppBillRecordPo> waterList = new ArrayList<> ();
            List<String> userIds = new ArrayList<> ();
            List<AppUserPo> appUserPoList = appUserService.listWaitingReturnWaterUser ();
            String bunessNum = ToolUtils.getUUID ();
            for (AppUserPo po : appUserPoList) {
                SysUserVo sysUserVo = sysUserService.findById (po.getParentId ());
                SysAgentSettingPo sysAgentSettingPo = sysAgentSettingService.findById (sysUserVo.getAgentLevelId ());
                //获取赔率
                BigDecimal rate = sysAgentSettingPo.getReturnWaterScale ();
                AppBillRecordPo billRecordPo = new AppBillRecordPo ();
                billRecordPo.setId (ToolUtils.getUUID ());
                billRecordPo.setUserId (po.getId ());
                billRecordPo.setBeforeBalance (BigDecimal.ONE);
                billRecordPo.setAfterBalance (BigDecimal.ZERO);
                billRecordPo.setBalance (po.getKickBackAmount ().multiply (rate).setScale (2, BigDecimal.ROUND_HALF_EVEN));
                billRecordPo.setBusinessNumber (bunessNum);
                billRecordPo.setBusnessType (BusnessTypeEnum.RETURN_WATER.getCode ());
                billRecordPo.setCreateTime (new Date ());
                billRecordPo.setRemark ("代理返水结算,此次返水基数是:" + po.getKickBackAmount () + "，返水比例是:" + sysAgentSettingPo.getReturnWaterScale ());
                billRecordPo.setExtend ("");
                waterList.add (billRecordPo);
                userIds.add (po.getId ());
            }
            appUserService.returnWaterService (waterList, userIds);

        } catch (Exception e) {
            e.printStackTrace ();
        }
        List<AppTimeLotteryPo> list = appTimeLotteryService.lotteryListCurrentDay ();
        for (AppTimeLotteryPo po : list) {
            AppTimeLotteryPo model = appTimeLotteryService.findById (po.getIssueNo ());
            if (model != null) {
                break;
            } else {
                po.setId (ToolUtils.getUUID ());
                po.setLotteryTime (null);
                po.setFlag (LotteryFlagEnum.NO.getCode ());
                appTimeLotteryService.save (po);
            }
        }
    }


}
