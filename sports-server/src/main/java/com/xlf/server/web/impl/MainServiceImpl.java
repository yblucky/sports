package com.xlf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;

import com.xlf.server.mapper.SysResourceMapper;
import org.springframework.stereotype.Service;

import com.xlf.common.vo.pc.SysResourceVo;
import com.xlf.server.web.MainService;

/**
 * 主界面业务层实现类
 * @author qsy
 * @version v1.0
 * @date 2017年6月14日
 */
@Service
public class MainServiceImpl implements MainService {
	@Resource
	public SysResourceMapper resourceMapper;
	
	/**
	 * 加载菜单按钮列表
	 * @return 集合
	 */
	@Override
	public List<SysResourceVo> findMenu(String roleId){
		return resourceMapper.findMenus(roleId);
	}
}
