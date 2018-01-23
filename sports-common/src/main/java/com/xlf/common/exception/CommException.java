package com.xlf.common.exception;

/**
 * 自定义异常
 * @author qsy
 * @version v1.0
 * @date 2017年7月26日
 */
public class CommException extends RuntimeException {
	private static final long serialVersionUID = -1242639751003917776L;
	
	/**
	 * 构造
	 */
	public CommException(String msg) {
		super(msg);
	}
	
}
