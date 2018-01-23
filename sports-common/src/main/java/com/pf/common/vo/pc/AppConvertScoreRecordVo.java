package com.pf.common.vo.pc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 积分兑换记录
 * @author zyc
 *@date 2017.10.16
 */

@Table(name = "app_convertScore_record")
public class AppConvertScoreRecordVo {

    @Id
    private String id;
    /**
     * '用户id'
     */
    
    /**
     * 用户Uid
     */
    private Integer uid;
    /**
     * 用户nickName
     */
    private String nickName;
    /**
     * 用户mobile
     */
    private String mobile;
    /**
    private String userId;
    /**
     * '兑换余额数量'
     */
    private BigDecimal balance;
    /**
     * '兑换积分'
     */
    private BigDecimal convertScore;
    /**
     * 类型'10 - 余额转换积分'
     */
    private String recordType;
    
    private Date createTime;
    
    
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    /**
     * 备注
     */
    private String remark;
    /**
     *
     */
    private BigDecimal userBalance;
    /**
     * '兑换比例'
     */
    private BigDecimal scale;
    
    
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getConvertScore() {
		return convertScore;
	}
	public void setConvertScore(BigDecimal convertScore) {
		this.convertScore = convertScore;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getUserBalance() {
		return userBalance;
	}
	public void setUserBalance(BigDecimal userBalance) {
		this.userBalance = userBalance;
	}
	public BigDecimal getScale() {
		return scale;
	}
	public void setScale(BigDecimal scale) {
		this.scale = scale;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}	
  	
    
    
    



}
