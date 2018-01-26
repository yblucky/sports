package com.xlf.server.mapper;
import java.util.List;

import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
	@Select("select * from sys_user where loginName=#{ln} and ifnull(roleType,'')=#{roleType}")
	public SysUserVo findByloginName(@Param("ln") String loginName, @Param("roleType") Integer roleType);

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
	 * @param roleType
	 * @return
	 */
	@Select("select id,userName,loginName,mobile,roleId,roleName,userIcon,createTime,lastTime,state from sys_user order where roleType=#{roleType} by createTime desc")
	public List<SysUserVo> findAll(RowBounds rwoBounds, @Param("roleType") String roleType);

	/**
	 * 查总记录数
	 * @return
	 * @param roleType
	 */
	@Select("select count(1) from sys_user where roleType=#{roleType}")
	public long findCount(@Param("roleType") String roleType);

	/**
	 * 查找登录名是否存在
	 * @param loginName
	 * @return
	 */
	@Select("select count(1) from sys_user where loginName=#{ln}")
	public int findLoginName(@Param("ln") String loginName);

}
