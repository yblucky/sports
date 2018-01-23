package com.pf.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 网络工具类
 * 
 * @author qsy
 * @version v1.0
 * @date 2016年11月28日
 */
public class NetworkUtil {
	/**
	 * 获取请求IP地址
	 * @param request 请求对象
	 * @return ip地址
	 */
	public final static String getIpAddress(HttpServletRequest request) {
		try {
			String ip = request.getHeader("X-Forwarded-For");
			LogUtils.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);

			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("Proxy-Client-IP");
					LogUtils.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);

				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("WL-Proxy-Client-IP");
					LogUtils.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);

				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("HTTP_CLIENT_IP");
					LogUtils.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);

				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("HTTP_X_FORWARDED_FOR");
					LogUtils.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);

				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getRemoteAddr();
					LogUtils.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);

				}
			} else if (ip.length() > 15) {
				String[] ips = ip.split(",");
				for (int index = 0; index < ips.length; index++) {
					String strIp = (String) ips[index];
					if (!("unknown".equalsIgnoreCase(strIp))) {
						ip = strIp;
						break;
					}
				}
			}
			return ip;
		} catch (Exception ex) {
			LogUtils.error(ex.getMessage(), ex);
			return "0:0:0:0:0:0:0:1";
		}
	}
}
