package com.xlf.common.util;

import com.xlf.common.enums.LanguageEnum;
import com.xlf.common.exception.CommException;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 短信工具类
 * @author jay.zheng
 * @version v1.0
 * @date 2017年09月20日
 */
public class NewSmsUtil {
	/**
	 * 申请的序列号
	 */
	private String cn_sn;
	/**
	 * 密码
	 */
	private String cn_password;
	/**
	 * 密码md5(sn+password) 32位大写密文
	 */
	private String cn_pwd;
	/**
	 * 申请的序列号
	 */
	private String en_sn;
	/**
	 * 密码
	 */
	private String en_password;
	/**
	 * 密码md5(sn+password) 32位大写密文
	 */
	private String en_pwd;
	/**
	 * 扩展码
	 */
	private String ext;
	/**
	 * 定时时间
	 */
	private String stime;
	/**
	 * 唯一标识
	 */
	private String rrid;
	/**
	 * 国内短信请求URL的集合
	 */
	private List<String> cnUrl;
	/**
	 * 国际短信请求URL的集合
	 */
	private List<String> enUrl;
	/**
	 * 验证码有效时间
	 */
	private int outTime;

	/**
	 * 发送指定模板的短信
	 * @param mobile 手机号
	 * @param contact 内容
	 * @param templateId 模板
	 * @throws Exception 异常对象
	 */
	public void sendSms(String language,String mobile, String contact, String templateId) throws Exception{
		try{
			if(LanguageEnum.ZH_CN.getCode().equals(language)){
				//发送国内短信
				sendSmsByCn(mobile,contact,templateId);
			}else if(LanguageEnum.EN_US.getCode().equals(language)){
				//发送国际短信
				sendSmsByEN(mobile,contact,templateId);
			}
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
	public void sendSmsByCn(String mobile, String contact, String templateId) throws Exception{
		try{
			//替换模板中的参数
			String content=templateId.replaceAll("\\{1\\}",contact);
			//调用短信接口，优先调用主接口，忙碌或失败时调用备用接口
			for(String url : cnUrl){
				//默认中文字符要编码
				String msg = content;
				//这个8061比较特殊，说要utf-8编码，其实我也不懂为什么。。。
				if(url.indexOf("8061")>0){
					msg = URLEncoder.encode(content,"utf-8");
				}
				String rrid = ToolUtils.getOrderNo();
				String rs= HttpUtils.sendPost(url,"sn="+ cn_sn +"&pwd="+cn_pwd+"&mobile="+mobile+"&content="+msg+"&ext="+ext+"&stime=&rrid="+rrid+"&msgfmt=");
				if(!StringUtils.isEmpty(rs) && rs.indexOf(rrid)>0){
					LogUtils.info("发送短信成功");
					return;
				}
			}
			//代码如果执行到这里表示发送短信的接口都失败了，抛出异常通知发送短信失败
			throw new CommException("短信发送异常");
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
	public void sendSmsByEN(String mobile, String contact, String templateId) throws Exception{
		try{
			String content=templateId.replaceAll("\\{1\\}",contact);
			for(String url : enUrl){
				String rrid = ToolUtils.getOrderNo();
				String rs= HttpUtils.sendPost(url,"sn="+ en_sn +"&pwd="+en_pwd+"&mobile="+mobile+"&content="+content+"&ext="+ext+"&stime=&rrid="+rrid+"&msgfmt=");
				if(!StringUtils.isEmpty(rs) && rs.indexOf(rrid)>0){
					LogUtils.info("发送短信成功");
					return;
				}
			}
			//代码如果执行到这里表示发送短信的接口都失败了，抛出异常通知发送短信失败
			throw new CommException("短信发送异常");
		}catch(Exception ex){
			throw new Exception("短信发送异常", ex);
		}
	}

	public void setCn_sn(String cn_sn) {
		this.cn_sn = cn_sn;
	}

	public void setCn_password(String cn_password) {
		this.cn_password = cn_password;
		this.setCn_pwd(CryptUtils.getMD5(this.cn_sn +this.cn_password));
	}

	public void setCn_pwd(String cn_pwd) {
		this.cn_pwd = cn_pwd;
	}

	public void setEn_sn(String en_sn) {
		this.en_sn = en_sn;
	}

	public void setEn_password(String en_password) {
		this.en_password = en_password;
		this.setEn_pwd(CryptUtils.getMD5(this.en_sn +this.en_password));
	}

	public void setEn_pwd(String en_pwd) {
		this.en_pwd = en_pwd;
	}


	public void setExt(String ext) {
		this.ext = ext;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public void setRrid(String rrid) {
		this.rrid = rrid;
	}

	public void setCnUrl(String urls) {
		String[] arrUrl = null;
		if(!StringUtils.isEmpty(urls) && urls.length() >0) {
			arrUrl = urls.split(",");
		}
		List<String> cnUrl = new ArrayList<String>();
		for(String url :arrUrl){
			cnUrl.add(url);
		}
		this.cnUrl = cnUrl;
	}

	public void setEnUrl(String urls) {
		String[] arrUrl = null;
		if(!StringUtils.isEmpty(urls) && urls.length() >0) {
			arrUrl = urls.split(",");
		}
		List<String> enUrl = new ArrayList<String>();
		for(String url :arrUrl){
			enUrl.add(url);
		}
		this.enUrl = enUrl;
	}

	public void setOutTime(int outTime) {
		this.outTime = outTime;
	}

	public String getCn_sn() {
		return cn_sn;
	}

	public String getCn_password() {
		return cn_password;
	}

	public String getCn_pwd() {
		return cn_pwd;
	}

	public String getEn_sn() {
		return en_sn;
	}

	public String getEn_password() {
		return en_password;
	}

	public String getEn_pwd() {
		return en_pwd;
	}

	public String getExt() {
		return ext;
	}

	public String getStime() {
		return stime;
	}

	public String getRrid() {
		return rrid;
	}

	public List<String> getCnUrl() {
		return cnUrl;
	}

	public List<String> getEnUrl() {
		return enUrl;
	}

	public int getOutTime() {
		return outTime;
	}
}
