package com.xlf.common.util;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 加密工具类
 * 
 * @author qsy
 * @version v1.0
 * @date 2017年6月26日
 */
public final class CryptUtils {

	private CryptUtils() {
		super();
	}

	private static final String DES = "DES";

	private static final String KEY = "baotongsu";

	public static String hmacSHA1Encrypt(String encryptText, String salt) {
		byte[] encryData = null;
		try {
			byte[] data = salt.getBytes("UTF-8");
			SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKey);
			byte[] text = encryptText.getBytes("UTF-8");
			encryData = mac.doFinal(text);
		} catch (UnsupportedEncodingException e) {
			LogUtils.error("HmacSHA1无效编码");
		} catch (InvalidKeyException e) {
			LogUtils.error("HmacSHA1无效密钥");
		} catch (NoSuchAlgorithmException e) {
			LogUtils.error("HmacSHA1无效算法参数");
		}

		// 完成 Mac 操作
		return byteTwoHex(encryData);
	}

	public static String sha1(String encryptText) {
		byte[] diData = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(encryptText.getBytes());
			diData = md.digest();
		} catch (NoSuchAlgorithmException e) {
			LogUtils.error("SHA1无效算法参数");
		}
		return byteTwoHex(diData);
	}

	/**
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		try {
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(DES);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(src);
		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance(DES);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
			// 现在，获取数据并解密
			// 正式执行解密操作
			return cipher.doFinal(src);
		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 数据解密
	 * 
	 * @param data
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static final String decrypt(String data) throws Exception {
		return new String(decrypt(hexTwoByte(data.getBytes()), KEY.getBytes()));
	}

	/**
	 * 数据加密
	 * 
	 * @param data
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static final String encrypt(String data) {
		if (data != null) {
			try {
				return byteTwoHex(encrypt(data.getBytes(), KEY.getBytes()));
			} catch (Exception e) {
				LogUtils.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	
    /** 
     * MD5数字签名 
     * @param src 
     * @return 
     * @throws Exception 
     */  
    public static String md5Digest(String src) throws Exception {  
       // 定义数字签名方法, 可用：MD5, SHA-1  
       MessageDigest md = MessageDigest.getInstance("MD5");  
       byte[] b = md.digest(src.getBytes("UTF-8"));  
       return byte2HexStr(b);  
    }
    
    /**
     * 签名
     * @param accountSid 开发者ID
     * @param authToken token
     * @return
     * @throws Exception
     */
	public static String getSignature(String accountSid, String authToken, String timestamp) throws Exception{
		String sig = accountSid + authToken + timestamp;
		String signature = md5Digest(sig);
		return signature;
	}
	
    /** 
     * BASE64编码
     * @param src 
     * @return 
     * @throws Exception 
     */  
    public static String base64Encoder(String src) throws Exception {
        return Base64.getEncoder().encodeToString(src.getBytes("UTF-8"));
    }  
      
    /** 
     * BASE64解码
     * @param dest 
     * @return 
     * @throws Exception 
     */  
    public static String base64Decoder(String dest) throws Exception {
        return new String(Base64.getDecoder().decode(dest), "UTF-8");
    }

	/**
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byteTwoHex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				hs.append('0');
			}
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	private static byte[] hexTwoByte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException();
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	
    /** 
     * 字节数组转化为大写16进制字符串 
     * @param b 
     * @return 
     */  
    private static String byte2HexStr(byte[] b) {  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < b.length; i++) {  
            String s = Integer.toHexString(b[i] & 0xFF);  
            if (s.length() == 1) {  
                sb.append("0");  
            }  
            sb.append(s.toUpperCase());  
        }  
        return sb.toString();  
    }

	public static String getMD5(String sourceStr) {
		String resultStr = "";
		try {
			byte[] temp = sourceStr.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(temp);
			// resultStr = new String(md5.digest());
			byte[] b = md5.digest();
			for (int i = 0; i < b.length; i++) {
				char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
						'9', 'A', 'B', 'C', 'D', 'E', 'F' };
				char[] ob = new char[2];
				ob[0] = digit[(b[i] >>> 4) & 0X0F];
				ob[1] = digit[b[i] & 0X0F];
				resultStr += new String(ob);
			}
			return resultStr;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
