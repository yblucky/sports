package com.xlf.common.vo.pc;

import com.xlf.common.po.UseImagePo;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 查询VO
 * Created by Administrator on 2017/8/17.
 */
public class LotteryVo {

    /**
     * '主键编号'
     */
	private String id;
	/**
	 * 游戏彩种
	 * */
	private String gameType;
	/**
	 * 游戏彩种
	 * */
	private String issueNo;
	/**
	 * 用户id
	 * */
	private String userId;
	/**
	 * 用户id
	 * */
	private String parentId;
	/**
	 * 用户id
	 * */
	private String mobile;
	/**
	 * 开奖结果
	 * */
	private String lotteryResult;
	/**
	 * 游戏状态
	 * */
	private String gameState;
	/**
	 * 用户uid
	 * */
	private String uid;
	/**
	 * 用户昵称
	 * */
	private String nickname;
	/**
	 * 投注类型
	 * */
	private String betType;
	/**
	 * 投注内容
	 * */
	private String betContent;
	/**
	 * 投注金额
	 * */
	private String betAmount;
	/**
	 * 输赢情况
	 * */
	private String winningAmount;
	/**
	 * 投注时间
	 * */
	private Date createTime;
	/**
	 * 状态
	 * */
	private String state;
	/**
	 * 业务单号
	 * */
	private String businessNumber;
	/**
	 * 时间类型 10 今日 20 昨天 30 上周 40 本周 50 上月 60 本月
	 * */
	private String dateType;
	/**
	 * 时间相差天数
	 * */
	private int startDiff;
	/**
	 * 时间相差天数
	 * */
	private int endDiff;
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

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLotteryResult() {
		return lotteryResult;
	}

	public void setLotteryResult(String lotteryResult) {
		this.lotteryResult = lotteryResult;
	}

	public String getGameState() {
		return gameState;
	}

	public void setGameState(String gameState) {
		this.gameState = gameState;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	public String getBetContent() {
		return betContent;
	}

	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}

	public String getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(String betAmount) {
		this.betAmount = betAmount;
	}

	public String getWinningAmount() {
		return winningAmount;
	}

	public void setWinningAmount(String winningAmount) {
		this.winningAmount = winningAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public int getStartDiff() {
		return startDiff;
	}

	public void setStartDiff(int startDiff) {
		this.startDiff = startDiff;
	}

	public int getEndDiff() {
		return endDiff;
	}

	public void setEndDiff(int endDiff) {
		this.endDiff = endDiff;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
