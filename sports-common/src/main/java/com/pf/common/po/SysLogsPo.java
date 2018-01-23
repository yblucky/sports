package com.pf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统日志记录持久类
 * @author qsy
 * @version v1.0
 * @date 2016年11月25日
 */
@Table(name="sys_logs")
public class SysLogsPo {
	/**
	 * 主键编号
	 */
	@Id
	private String id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 操作类型
	 */
	private String optType;
	/**
	 * 操作时间
	 */
	private Date optDate;
	/**
	 * 详细信息
	 */
	private String logDetail;
	/**
	 * IP
	 */
	private String ip;
	/**
	 * 操作模块
	 */
	private String optModule;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the optType
	 */
	public String getOptType() {
		return optType;
	}
	/**
	 * @param optType the optType to set
	 */
	public void setOptType(String optType) {
		this.optType = optType;
	}
	/**
	 * @return the optDate
	 */
	public Date getOptDate() {
		return optDate;
	}
	/**
	 * @param optDate the optDate to set
	 */
	public void setOptDate(Date optDate) {
		this.optDate = optDate;
	}
	/**
	 * @return the logDetail
	 */
	public String getLogDetail() {
		return logDetail;
	}
	/**
	 * @param logDetail the logDetail to set
	 */
	public void setLogDetail(String logDetail) {
		this.logDetail = logDetail;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the optModule
	 */
	public String getOptModule() {
		return optModule;
	}
	/**
	 * @param optModule the optModule to set
	 */
	public void setOptModule(String optModule) {
		this.optModule = optModule;
	}
	
}
