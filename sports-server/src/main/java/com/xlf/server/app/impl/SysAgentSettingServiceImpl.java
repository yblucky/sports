package com.xlf.server.app.impl;

import com.xlf.common.enums.BankEnum;
import com.xlf.common.enums.StateEnum;
import com.xlf.common.enums.YNEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppBankCardPo;
import com.xlf.common.po.AppBankTypePo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.MyBeanUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.BankCardVo;
import com.xlf.server.app.SettingService;
import com.xlf.server.app.SysAgentSettingService;
import com.xlf.server.common.CommonService;
import com.xlf.server.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 设置业务层接口
 *
 * @author jay
 * @version v1.0
 * @date 2017年8月18日
 */
@Service
public class SysAgentSettingServiceImpl implements SysAgentSettingService {

    @Resource
    private SysAgentSettingMapper sysAgentSettingMapper;


    @Override
    public List<SysAgentSettingPo> loadAgentSetting() {
        return sysAgentSettingMapper.selectAll();
    }

    @Override
    public SysAgentSettingPo findById(String id) {
        if (StringUtils.isEmpty(id)){
            return null;
        }
        return sysAgentSettingMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(SysAgentSettingPo vo) {
        vo.setId(ToolUtils.getUUID());
        vo.setCreateTime(new Date());
        vo.setState(StateEnum.NORMAL.getCode());
        sysAgentSettingMapper.insertSelective(vo);
    }

    @Override
    public void update(SysAgentSettingPo vo) {
        sysAgentSettingMapper.updateByPrimaryKeySelective(vo);
    }

    @Override
    public void delete(SysAgentSettingPo vo) {
        sysAgentSettingMapper.deleteByPrimaryKey(vo);
    }
}
