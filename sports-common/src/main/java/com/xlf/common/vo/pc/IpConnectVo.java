package com.xlf.common.vo.pc;


/**
 * 
 * @author zyc
 * ip访问次数
 *
 */

public class IpConnectVo {
	
	private String ipAddress;  //ip
	
	private Integer connectCount; //次数

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getConnectCount() {
		return connectCount;
	}

	public void setConnectCount(Integer connectCount) {
		this.connectCount = connectCount;
	}
	
	
	
	
	
	
}
