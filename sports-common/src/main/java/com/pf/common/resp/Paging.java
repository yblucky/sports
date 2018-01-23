package com.pf.common.resp;

import org.apache.ibatis.session.RowBounds;

/**
 * 分页
 * @author qsy
 * @version v1.0
 * @date 2016年11月25日
 */
public class Paging extends RowBounds{
	/**
	 * 页数
	 */
	protected int	pageNumber	= 1;
	/**
	 * 显示条数
	 */
	protected int	pageSize	= 20;
	/**
	 * 总记录数
	 */
	protected long	totalCount	= 0;
	/**
	 * 总页数
	 */
	protected int	pageCount	= 0;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize <= 0) {
			this.pageSize = 20;
		}
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		long size = (long) pageSize;
		setPageCount((int) (totalCount / size + (totalCount % size == 0 ? 0 : 1)));
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}
