package com.xlf.server.web.impl;

import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.server.app.AppSysAgentSettingService;
import com.xlf.server.mapper.SysAgentSettingMapper;
import com.xlf.server.web.WebSysAgentSettingService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service
public class WebSysAgentSettingImpl implements WebSysAgentSettingService {

    @Resource private SysAgentSettingMapper webSysAgentSettingMapper;
    @Override
    public SysAgentSettingPo findById(String id) {
        if (StringUtils.isEmpty(id)){
            return null;
        }
        return webSysAgentSettingMapper.selectByPrimaryKey(id);
    }
}
