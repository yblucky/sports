package com.pf.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 连接虚拟货币信息
 * @author qsy
 * @version V1.0
 * @date 2017年8月17日
 */
@Component
public class BitCoinUtil {
	@Value("${bitCoin.host}")
	private String host;
	@Value("${bitCoin.login}")
	private String login;
	@Value("${bitCoin.password}")
	private String password;
	@Value("${bitCoin.port}")
	private int port;
	@Value("${bitCoin.exrateUrl}")
	private String exrateUrl;
	@Value("${bitCoin.timeout}")
	private int timeout;
	
	public String getExrateUrl() {
		return exrateUrl;
	}
	public void setExrateUrl(String exrateUrl) {
		this.exrateUrl = exrateUrl;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
