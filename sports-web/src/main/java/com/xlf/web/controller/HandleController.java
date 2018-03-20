/* 
 * 文件名：MainController.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月14日
 * 版本号：v1.0
*/
package com.xlf.web.controller;

import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.server.app.AppTimeLotteryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 财务控制器
 *
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@RestController
@RequestMapping(value = "/handle")
public class HandleController {
    @Resource
    private AppTimeLotteryService appTimeLotteryService;


    @GetMapping("/open")
    public RespBody open() {
        RespBody respBody = new RespBody();
        try {
            appTimeLotteryService.timeOpenTask();
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "手动开奖成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "手动开奖失败");
            LogUtils.error("手动开奖失败！", ex);
        }
        return respBody;
    }

}




