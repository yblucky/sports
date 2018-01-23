/* 
 * 文件名：updatePwVo.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月23日
 * 版本号：v1.0
*/
package com.pf.common.vo.pc;

/**
 * 修改密码VO类
 * @author qsy
 * @version v1.0
 * @date 2017年6月23日
 */
public class UpdatePwVo {
	/**
	 * 旧密码
	 */
	private String oldPw;
	/**
	 * 新密码
	 */
	private String newPw;
	/**
	 * 确认码
	 */
	private String okPw;
	/**
	 * @return the oldPw
	 */
	public String getOldPw() {
		return oldPw;
	}
	/**
	 * @param oldPw the oldPw to set
	 */
	public void setOldPw(String oldPw) {
		this.oldPw = oldPw;
	}
	/**
	 * @return the newPw
	 */
	public String getNewPw() {
		return newPw;
	}
	/**
	 * @param newPw the newPw to set
	 */
	public void setNewPw(String newPw) {
		this.newPw = newPw;
	}
	/**
	 * @return the okPw
	 */
	public String getOkPw() {
		return okPw;
	}
	/**
	 * @param okPw the okPw to set
	 */
	public void setOkPw(String okPw) {
		this.okPw = okPw;
	}
}
