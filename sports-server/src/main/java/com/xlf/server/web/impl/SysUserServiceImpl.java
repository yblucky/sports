package com.xlf.server.web.impl;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.po.SysUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.CryptUtils;
import com.xlf.common.util.MyBeanUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.pc.SysRoleVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.mapper.SysRoleMapper;
import com.xlf.server.mapper.SysUserMapper;
import com.xlf.server.web.SysUserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户业务层实现类
 *
 * @author qsy
 * @version v1.0
 * @date 2017年6月12日
 */
@Service("webUserService")
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private RedisService redisService;
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private AppBillRecordService appBillRecordService;

    @Override
    public SysUserVo SysUserVo(String token) throws Exception {
        SysUserVo userVo = null;
        Object obj = redisService.getObj (token);
        if (obj != null && obj instanceof SysUserVo) {
            userVo = (SysUserVo) obj;
        }
        return userVo;
    }

    @Override
    public List<SysUserVo> findAll(Paging paging, SysUserVo vo) {
        RowBounds rwoBounds = new RowBounds (paging.getPageNumber (), paging.getPageSize ());
        return userMapper.findAll (rwoBounds, vo);
    }

    @Override
    public long findCount(SysUserVo vo) {
        return userMapper.findCount (vo);
    }

    @Override
    public void add(SysUserVo userVo) throws Exception {
        SysUserPo userPo = MyBeanUtils.copyProperties (userVo, SysUserPo.class);
        String salt = ToolUtils.getUUID ();
        String loginPw = CryptUtils.hmacSHA1Encrypt (userVo.getPassword (), salt);
        userPo.setPassword (loginPw);
        userPo.setSalt (salt);
        userPo.setId (ToolUtils.getUUID ());
        userPo.setCreateTime (new Date ());
        userPo.setTotayReturnWater(BigDecimal.ZERO);
        userPo.setTotalReturnWater(BigDecimal.ZERO);
        userPo.setBalance(BigDecimal.ZERO);
        userMapper.insert (userPo);
    }

    @Override
    public void update(SysUserVo userVo) throws Exception {
        SysUserPo userPo = userMapper.selectByPrimaryKey (userVo.getId ());
        userPo.setUserName (userVo.getUserName ());
        userPo.setMobile (userVo.getMobile ());
        userPo.setRoleId (userVo.getRoleId ());
        userPo.setRoleName (userVo.getRoleName ());
        userPo.setState (userVo.getState ());
        userPo.setAgentLevelId (userVo.getAgentLevelId ());
        userMapper.updateByPrimaryKey (userPo);
    }


    @Override
    public void delete(SysUserVo userVo) {
        userMapper.deleteByPrimaryKey (userVo.getId ());
    }

    @Override
    public List<SysRoleVo> findRoles() throws Exception {
        return MyBeanUtils.copyList (roleMapper.selectAll (), SysRoleVo.class);
    }

    @Override
    public SysUserVo findByLoginName(String loginName) {
        return userMapper.findByloginName (loginName);
    }

    @Override
    public SysUserVo findByMobile(String mobile) {
        return userMapper.findByMobile (mobile);
    }

    @Override
    public void updatePw(String newPw, String id) {
        SysUserPo userPo = userMapper.selectByPrimaryKey (id);
        userPo.setPassword (newPw);
        userMapper.updateByPrimaryKey (userPo);
    }

    @Override
    public SysUserVo findById(String id) {
        return userMapper.findById (id);
    }


    @Override
    public SysUserVo getUserByToken(String token) throws Exception {
        SysUserVo user = null;
        Object obj = redisService.getObj (token);
        if (obj != null && obj instanceof SysUserVo) {
            user = (SysUserVo) obj;
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recharge(SysUserVo find, BigDecimal balance) throws Exception {

        int rows = userMapper.updateBalance (find.getId (), balance);
        if (rows <= 0) {
            throw new CommException ("充值失败！！！");
        }
        //流水记录
        appBillRecordService.saveBillRecord (ToolUtils.getOrderNo (), find.getId (), BusnessTypeEnum.BACK_RECHARGE.getCode ()
                , balance, balance, balance.add (find.getBalance()), "后台充值", "");
    }

    @Override
    public Integer updateBalance(String id, BigDecimal balance){
        Integer count = userMapper.updateBalance (id,balance);
        if (count==null){
            count=0;
        }
        return count;
    }
    @Override
    public Integer updateReturnWater(String id,BigDecimal todayWater, BigDecimal totalWater) {
        Integer count = userMapper.updateReturnWater (id,todayWater, totalWater);
        return count;
    }

    @Override
    public Integer updateClearTotayReturnWater() {
        Integer count = userMapper.updateClearTotayReturnWater ();
        return count;
    }
}
