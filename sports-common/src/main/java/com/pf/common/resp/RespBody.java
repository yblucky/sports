package com.pf.common.resp;

import com.pf.common.enums.RespCodeEnum;

/**
 * 响应对象
 * @author qsy
 * @version v1.0
 * @date 2016年11月25日
 */
public class RespBody {
	private String code = RespCodeEnum.SUCCESS.getCode();
	private String message = "成功";
	private Paging page;
	private Object data;

	public void add(String code,String message){
		this.code = code;
		this.message = message;
	}
	
	public void add(String code,String message,Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public void add(String code,String message,Paging page,Object data){
		this.code = code;
		this.message = message;
		this.page = page;
		this.data = data;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the page
	 */
	public Paging getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(Paging page) {
		this.page = page;
	}
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
}
