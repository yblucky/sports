package com.xlf.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取资源文件
 * @author qsy
 * @version v1.0
 * @date 2017年7月27日
 */
@Component
public class ConfUtils {
	/**
	 * 会话超时，单位秒
	 */
	@Value("${user.sessionTimeout}")
	private int sessionTimeout;

	/**
	 * @return the sessionTimeout
	 */
	public int getSessionTimeout() {
		return sessionTimeout;
	}

	/**
	 * @param sessionTimeout the sessionTimeout to set
	 */
	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
	
	
}
