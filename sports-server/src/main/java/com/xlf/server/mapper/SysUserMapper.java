package com.xlf.server.mapper;
import java.math.BigDecimal;
import java.util.List;

import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.xlf.common.po.SysUserPo;
import com.xlf.common.vo.pc.SysUserVo;

/**
 * 用户信息DAO
 * @author qsy
 * @version v1.0
 * @date 2017年6月6日
 */
public interface SysUserMapper extends BaseMapper<SysUserPo> {

	/**
	 * 用户登录
	 * @param loginName
	 * @param roleType
     * @return
	 */
	@Select("select * from sys_user where loginName=#{ln}")
	public SysUserVo findByloginName(@Param("ln") String loginName);

	/**
	 * 用户登录
	 * @param mobile
	 * @return
	 */
	@Select("select * from sys_user where mobile=#{mobile}")
	public SysUserVo findByMobile(@Param("mobile") String mobile);

	/**
	 * @param id
	 * @return
	 */
	@Select("select * from sys_user where id=#{id}")
	public SysUserVo findById(@Param("id") String id);

	/**
	 * 分页查询
	 * @param rwoBounds
	 * @param vo
	 * @return
	 */
/*	@Select("select u.id,u.userName,u.loginName,u.mobile,u.roleId,u.roleName,u.roleType,u.userIcon,u.createTime,u.lastTime,u.state,u.totayReturnWater,u.totalReturnWater,t.agentName,u.agentLevelId " +
			"  from sys_user u left join sys_agent_setting t on u.agentLevelId=t.id where roleType=#{roleType} order by u.createTime desc")*/
	public List<SysUserVo> findAll(RowBounds rwoBounds, @Param("model") SysUserVo vo);

	/**
	 * 查总记录数
	 * @return
	 * @param roleType
	 */
//	@Select("select count(1) from sys_user where roleType=#{roleType}")
	public long findCount(@Param("model") SysUserVo roleType);

	/**
	 * 查找登录名是否存在
	 * @param loginName
	 * @return
	 */
	@Select("select count(1) from sys_user where loginName=#{ln}")
	public int findLoginName(@Param("ln") String loginName);

	@Update("update sys_user  set balance = ifnull(balance,0) + #{balance}   where id=#{id} and (ifnull(balance,0) + #{balance}) >= 0")
	int updateBalance(@Param("id") String id, @Param("balance") BigDecimal balance);

	@Update("update sys_user  set totayReturnWater =totayReturnWater+ #{totayReturnWater} ,totalReturnWater=totalReturnWater+#{totalReturnWater},returnWaterTime=NOW()  where id=#{id}")
    Integer updateReturnWater(@Param ("id") String id,@Param("totayReturnWater") BigDecimal todayWater,@Param ("totalReturnWater") BigDecimal totalWater);

	@Update("update sys_user  set totayReturnWater =0 ,clearReturnWaterTime=now()   where hour(TIMEDIFF(now(),clearReturnWaterTime))>=20")
    Integer updateClearTotayReturnWater();
}
