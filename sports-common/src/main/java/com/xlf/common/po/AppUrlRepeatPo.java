package com.xlf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * URL验签Po类
 * @author qsy
 * @version v1.0
 * @date 2016年12月15日
 */
@Table(name="app_url_repeat")
public class AppUrlRepeatPo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * URL记录编号
	 */
	@Id
	private String id;
	
	/**
	 * URL
	 */
	private String url;
	/**
	 * 备注
	 */
	private String remark;
	
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


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
