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
}
