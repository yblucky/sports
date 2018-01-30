package com.xlf.server.app;

import com.xlf.common.po.SysAgentSettingPo;

import java.util.List;

/**
 * 代理等级参数相关业务
 * Created by Administrator on 2018/1/4 0004.
 */
public interface SysAgentSettingService {

    List<SysAgentSettingPo> loadAgentSetting();

    SysAgentSettingPo findById(String id);
}
