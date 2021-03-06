package com.xlf.server.app.impl;

import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.server.app.AppSysAgentSettingService;
import com.xlf.server.mapper.SysAgentSettingMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service
public class AppSysAgentSettingImpl implements AppSysAgentSettingService {


    @Resource private SysAgentSettingMapper appSysAgentSettingMapper;
    @Override
    public SysAgentSettingPo findById(String id) {
        if (StringUtils.isEmpty(id)){
            return null;
        }
        return appSysAgentSettingMapper.selectByPrimaryKey(id);
    }
}
