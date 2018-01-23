/* 
 * 文件名：BaseResourcePo.java  
 * 版权：Copyright 2016-2016 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友
 * 创建时间：2016年12月6日
 * 版本号：v1.0
*/
package com.pf.common.vo.pc;

/**
 * 资源FormVo
 * @author qsy
 * @version v1.0
 * @date 2016年12月6日
 */
public class SysResourceVo {
	
	/**
	 * 主键编号	
	 * */
	private String id;
	
	/**
	 * 资源名称
	 */
	private String resName;
	
	/**
	 * 资源路径
	 */
	private String resPath;
	
	/**
	 * 上级资源
	 */
	private String parentId;
	
	/**
	 * 资源图标
	 */
	private String resIcon;
	
	/**
	 * 资源描述
	 */
	private String resDesc;
	
	/**
	 * 是否展开
	 */
	private String resOpen;
	
	/**
	 * 资源顺序
	 */
	private Integer resSort;
	
	/**
	 * 状态	
	 */
	private String state;
	/**
	 * 是否选中
	 */
	private boolean selected = true;
	
	/**
	 * @return the selected
	 */
	public Boolean getSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return the resOpen
	 */
	public String getResOpen() {
		return resOpen;
	}

	/**
	 * @param resOpen the resOpen to set
	 */
	public void setResOpen(String resOpen) {
		this.resOpen = resOpen;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResPath() {
		return resPath;
	}

	public void setResPath(String resPath) {
		this.resPath = resPath;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getResIcon() {
		return resIcon;
	}

	public void setResIcon(String resIcon) {
		this.resIcon = resIcon;
	}

	public String getResDesc() {
		return resDesc;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}

	public Integer getResSort() {
		return resSort;
	}

	public void setResSort(Integer resSort) {
		this.resSort = resSort;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
