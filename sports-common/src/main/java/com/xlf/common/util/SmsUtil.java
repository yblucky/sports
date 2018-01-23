package com.xlf.common.util;

/**
 * 短信工具类
 * @author qsy
 * @version v1.0
 * @date 2016年12月29日
 */
public class SmsUtil {
	/**
	 * 申请的key
	 */
	private String key;
	/**
	 * 请求URL
	 */
	private String url;
	/**
	 * 短信模板
	 */
	private String templateId;
	/**
	 * 验证码有效时间
	 */
	private int outTime;
	
	/**
	 * 获取手机验证吗
	 * @param mobile 手机号
	 * @param code 验证码
	 * @throws Exception 异常对象
	 */
	public void sendSms(String mobile,String code) throws Exception{
		try{
			HttpUtils.sendGet(url, "key="+key+"&mobile="+mobile+"&templateId="+templateId+"&param="+code);
		}catch(Exception ex){
			throw new Exception("短信发送异常", ex);
		}
	}
	/**
	 * 发送指定模板的短信
	 * @param mobile 手机号
	 * @param contact 内容
	 * @param templateId 模板
	 * @throws Exception 异常对象
	 */
	public void sendSms(String mobile,String contact,String templateId) throws Exception{
		try{
			HttpUtils.sendGet(url, "key="+key+"&mobile="+mobile+"&templateId="+templateId+"&param="+contact);
		}catch(Exception ex){
			throw new Exception("短信发送异常", ex);
		}
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
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
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the outTime
	 */
	public int getOutTime() {
		return outTime;
	}

	/**
	 * @param outTime the outTime to set
	 */
	public void setOutTime(int outTime) {
		this.outTime = outTime;
	}
}
