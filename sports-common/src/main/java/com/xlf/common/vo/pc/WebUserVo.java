/* 
 * 文件名：SysUserVo.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月6日
 * 版本号：v1.0
*/
package com.xlf.common.vo.pc;

import com.xlf.common.po.AppUserPo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户Vo类
 * 
 * @author qsy
 * @version v1.0
 * @date 2017年6月6日
 */
public class WebUserVo extends AppUserPo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1644042216784707890L;

	private String agentId;
	/**
	 * 用户名
	 */
	private String agentName;
	/**
	 * 用户名
	 */
	private String agentLevel;



	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}
}
