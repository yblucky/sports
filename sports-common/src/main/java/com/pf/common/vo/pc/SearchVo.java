package com.pf.common.vo.pc;

import com.pf.common.po.UseImagePo;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.pf.common.po.UseImagePo;

/**
 * 查询VO
 * Created by Administrator on 2017/8/17.
 */
public class SearchVo {

    /**
     * '主键编号'
     */
    private String id;
    /**
     * '用户编号'
     */
    private String userId;
    /**
     * '用户姓名'
     */
    private String userName;
    /**
     * '创建时间'
     */
    private Date createTime;
    /**
     * '数据状态'
     */
    private String state;
    /**
     * '用户uid'
     */
    private Integer uid;
    /**
     * '用户手机'
     */
    private String mobile;
    /**
     * '用户昵称'
     */
    private String nickName;
    /**
     * '订单ID'
     */
    private String OrderId;
    /**
     * '订单类型'
     */
    private String orderType;
    
    
    /**
     * 币种类型
     */
    
    private String  coinType;

    /**
     * '开始时间'
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    /**
     * '结束时间'
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    
    private List<UseImagePo> imgs;
    
    
    
    
    
    public String getCoinType() {
		return coinType;
	}

	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		if(null==endTime){
			return endTime;
		}else{
			return getEndtime(endTime.getTime());
		}
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<UseImagePo> getImgs() {
		return imgs;
	}

	public void setImgs(List<UseImagePo> imgs) {
		this.imgs = imgs;
	}
		
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	//结束时间处理
	public static Date getEndtime(Long endtime){
		Date endTime = new Date(endtime);
		try {
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			Calendar date = Calendar.getInstance();
			date.setTime(endTime);
			date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
			endTime = dft.parse(dft.format(date.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return endTime;
	}				
}
