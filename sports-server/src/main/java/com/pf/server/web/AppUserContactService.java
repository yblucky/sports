package com.pf.server.web;

import java.util.List;

import com.pf.common.vo.pc.AppUserContactVo;

public interface AppUserContactService {
	
	
	public List<AppUserContactVo> webFindUserByParentId(String parentId);
	
	
	public AppUserContactVo webfindUserByUserId(String userId);

}
