package com.pf.server.common.impl;

import com.google.code.kaptcha.Producer;
import com.pf.common.service.RedisService;
import com.pf.common.util.LogUtils;
import com.pf.common.util.ToolUtils;
import com.pf.server.common.KaptchaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 产生验证码业务层实现类
 * @author qsy
 * @version v1.0
 * @date 2016年12月30日
 */
@Service
public class KaptchaServiceImpl implements KaptchaService {
	@Resource
	private Producer captchaProducer;
	@Resource
	private RedisService reidsService;
	
	/**
	 * 生成验证码图片
	 * @return base64图片编码和图片uuid
	 * @throws Exception 异常对象
	 */
	@Override
	public Map<String,String> createCodeImg() throws Exception{
		Map<String,String> rmap = new HashMap<>();
		ByteArrayOutputStream baos =null;
		//创建图片UUID
		String uuid = ToolUtils.getUUID();
		try {
			//创建图片验证码
			String capText = captchaProducer.createText();
			BufferedImage bi = captchaProducer.createImage(capText);
	        baos = new ByteArrayOutputStream(); 
	        ImageIO.write(bi, "jpg", baos);
	        //将图片转换成base64编码字符串
	        String strImg = new String(Base64.getEncoder().encodeToString(baos.toByteArray()));
	        //保存到redis中
	        reidsService.putString(uuid, capText, 1800);
	        //保存到返回对象
	        rmap.put("imgKey", uuid);
	        rmap.put("imgStr", strImg);
		} catch (Exception ex) {
			LogUtils.error("验证码图片生成失败",ex);
			throw new Exception("验证码图片生成失败", ex);
		}finally{
			if(baos != null){
				baos.close();
			}
		}
		return rmap;
	}

}
