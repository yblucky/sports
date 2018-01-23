/* 
 * 文件名：KaptchaService.java  
 * 版权：Copyright 2016-2016 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2016年12月30日
 * 版本号：v1.0
*/
package com.xlf.server.common;

import java.util.Map;

/**
 * 产生验证码业务层接口
 * @author qsy
 * @version v1.0
 * @date 2016年12月30日
 */
public interface KaptchaService {

	/**
	 * 生成验证码图片
	 * @return base64图片编码和图片uuid
	 * @throws Exception 异常对象
	 */
	public Map<String, String> createCodeImg() throws Exception;
	
}
