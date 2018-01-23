/* 
 * 文件名：SysUrlRecordVo.java  
 * 版权：Copyright 2016-2016 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友 
 * 创建时间：2016年12月15日
 * 版本号：v1.0
*/
package com.pf.common.vo.pc;

import java.io.Serializable;

/**
 * URL记录Vo类
 * @author qsy
 * @version v1.0
 * @date 2016年12月15日
 */
public class SysUrlRecordVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * URL记录编号
	 */
	private String id;
	
	/**
	 * URL
	 */
	private String url;
	
	/**
	 * 是否有效
	 */
	private String state;
	
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}
