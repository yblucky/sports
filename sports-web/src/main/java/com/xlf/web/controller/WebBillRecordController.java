package com.xlf.web.controller;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.enums.RoleTypeEnum;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.common.vo.pc.WebBillRecordVo;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.WebBillRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;


@RestController
@RequestMapping("/webBill")
public class WebBillRecordController {
    @Resource
    private WebBillRecordService appBillRecordService;
    @Resource
    private CommonService commonService;

    @GetMapping("/findAll")
    public RespBody findAll(WebBillRecordVo vo, Paging paging) {
        RespBody respBody = new RespBody();
        try {
            //代理登录
            SysUserVo sysUser = commonService.checkWebToken();
            //只能查代理下面的会员
            if(RoleTypeEnum.AGENT.getCode().equals(sysUser.getRoleType())){
                vo.setParentId(sysUser.getId());
            }
            int total = appBillRecordService.findCount(vo);
            if(total >0) {
                //保存返回数据
                List<WebBillRecordVo> list = appBillRecordService.findAll(vo, paging);
                for (WebBillRecordVo vo1 : list) {
                    vo1.setBusnessTypeName(BusnessTypeEnum.getName(vo1.getBusnessType()));
                }
                respBody.add(RespCodeEnum.SUCCESS.getCode(), "查询记录数据成功", list);
                //保存分页对象
                paging.setTotalCount(total);
                respBody.setPage(paging);
            }else{
                respBody.add(RespCodeEnum.SUCCESS.getCode(), "查询记录数据成功", Collections.emptyList());
            }
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "查询记录数据失败");
            LogUtils.error("查找所有数据失败！", ex);
        }
        return respBody;
    }


    /**
     * 查找参数类型数据
     *
     * @return 响应信息
     */
    @GetMapping("/findBusnessType")
    public RespBody findBusnessType() {
        RespBody respBody = new RespBody();
        try {
            //保存返回数据
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();

            for (String str : appBillRecordService.getBusnessType()) {
                Map map = new HashMap<String, String>();
                map.put("key", str);
                map.put("val", BusnessTypeEnum.getName(Integer.parseInt(str)));
                list.add(map);
            }

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "参数类型查找所有数据成功", list);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "参数类型查找所有数据失败");
            LogUtils.error("参数类型查找所有数据失败！", ex);
        }
        return respBody;
    }



}
