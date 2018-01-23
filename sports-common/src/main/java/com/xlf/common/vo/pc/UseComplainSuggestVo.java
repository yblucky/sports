package com.xlf.common.vo.pc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.xlf.common.po.UseImagePo;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 投诉与建议po类
 * Created by Administrator on 2017/8/17.
 */
public class UseComplainSuggestVo {

    /**
     * '主键编号'
     */
    private String id;
    /**
     * '投诉人编号'
     */
    private String userId;
    /**
     * '投诉人姓名'
     */
    private String userName;
    /**
     * '投诉内容'
     */
    private String content;
    /**
     * '创建时间'
     */
    private Date createTime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
