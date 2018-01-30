package com.xlf.server.web.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.xlf.common.enums.WithDrawEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.po.AppUserPo;
import com.xlf.server.mapper.AppUserMapper;
import org.springframework.stereotype.Service;

import com.xlf.common.po.AppWithDrawPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.pc.AppWithDrawVo;
import com.xlf.server.mapper.AppWithDrawMapper;
import com.xlf.server.web.WebWithDrawService;


@Service("appWithDraw")
public class WebWithDrawServiceImpl implements WebWithDrawService {
	
	
	@Resource
	AppWithDrawMapper appWithDrawMapper;

	@Resource
	AppUserMapper appUserMapper;

	
	@Override
	public List<AppWithDrawVo> findAll(AppWithDrawVo vo, Paging paging) {
		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		return appWithDrawMapper.findAll(vo, startRow, pageSize);
	}

	@Override
	public Integer findCount(AppWithDrawVo vo) {
		// TODO Auto-generated method stub
		return appWithDrawMapper.findCount(vo);
	}

	@Override
	public void update(AppWithDrawPo po) {
		 
		 appWithDrawMapper.updateByPrimaryKey(po);
		
	}

	@Override
	public BigDecimal findSUM(AppWithDrawVo vo) {
		// TODO Auto-generated method stub
		return appWithDrawMapper.findSUM(vo);
	}

	@Override
	public int successState(String id) {
		AppWithDrawPo po = appWithDrawMapper.selectByPrimaryKey(id);
		if(po.getState().intValue() != WithDrawEnum.PENDING.getCode()){
			throw new CommException("此提现订单状态错误");
		}
		AppUserPo userPo = appUserMapper.findUserById(po.getUserId());
		if(userPo.getBlockedBalance().compareTo(po.getAmount())==-1){
			throw new CommException("用户冻结金额异常");
		}
		int rows = appUserMapper.updateBlockBalanceById(userPo.getId(),po.getAmount().multiply(new BigDecimal(-1)));
		if(rows <= 0){
			throw new CommException("确认打款失败");
		}
		AppWithDrawPo model = new AppWithDrawPo();
		model.setState(WithDrawEnum.SUCCESS.getCode());
		model.setId(po.getId());
		appWithDrawMapper.updateByPrimaryKeySelective(model);
		return 0;
	}

	@Override
	public void erroeState(String id) {
		AppWithDrawPo po = appWithDrawMapper.selectByPrimaryKey(id);
		if(po.getState().intValue() != WithDrawEnum.PENDING.getCode()){
			throw new CommException("此提现订单状态错误");
		}
		AppUserPo userPo = appUserMapper.findUserById(po.getUserId());
		if(userPo.getBlockedBalance().compareTo(po.getAmount())==-1){
			throw new CommException("用户冻结金额异常");
		}
		int rows = appUserMapper.updateBlockBalanceById(userPo.getId(),po.getAmount().multiply(new BigDecimal(-1)));
		if(rows <= 0){
			throw new CommException("确认打款失败");
		}
		rows = appUserMapper.updateBalanceById(userPo.getId(),po.getAmount());
		if(rows <= 0){
			throw new CommException("确认打款失败");
		}
		AppWithDrawPo model = new AppWithDrawPo();
		model.setState(WithDrawEnum.DENIED.getCode());
		model.setId(po.getId());
		appWithDrawMapper.updateByPrimaryKeySelective(model);
	}
}
