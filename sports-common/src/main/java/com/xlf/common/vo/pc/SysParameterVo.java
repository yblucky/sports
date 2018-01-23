/* 
 * 文件名：SysParameterVo.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.xlf.common.vo.pc;

import java.util.Date;

/**
 * 参数设置VO
 * 
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
public class SysParameterVo {
	/**
	 * 主键编号
	 */
	private String id;
	/**
	 * 参数名称
	 */
	private String paraName;
	/**
	 * 中文名称
	 */
	private String paraCnName;
	/**
	 * 参数值
	 */
	private String paraValue;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 有效标识
	 */
	private String state;
	
	/**
	 * 分组类型
	 */
	private String groupType;
	
	
	

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the paraName
	 */
	public String getParaName() {
		return paraName;
	}

	/**
	 * @param paraName
	 *            the paraName to set
	 */
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	/**
	 * @return the paraCnName
	 */
	public String getParaCnName() {
		return paraCnName;
	}

	/**
	 * @param paraCnName
	 *            the paraCnName to set
	 */
	public void setParaCnName(String paraCnName) {
		this.paraCnName = paraCnName;
	}

	/**
	 * @return the paraValue
	 */
	public String getParaValue() {
		return paraValue;
	}

	/**
	 * @param paraValue
	 *            the paraValue to set
	 */
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
}
