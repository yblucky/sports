package com.xlf.server.app.impl;

import com.xlf.common.enums.StateEnum;
import com.xlf.common.po.AppTimeIntervalPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.util.ToolUtils;
import com.xlf.server.app.AppTimeIntervalService;
import com.xlf.server.app.SysAgentSettingService;
import com.xlf.server.mapper.AppTimeIntervalMapper;
import com.xlf.server.mapper.SysAgentSettingMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class AppTimeIntervalServiceImpl implements AppTimeIntervalService {

    @Resource
    private AppTimeIntervalMapper appTimeIntervalMapper;



    @Override
    public AppTimeIntervalPo findById(String id) {
        if (StringUtils.isEmpty(id)){
            return null;
        }
        return appTimeIntervalMapper.selectByPrimaryKey(id);
    }
}
