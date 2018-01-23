package com.pf.server.web;

import java.util.List;

import com.pf.common.vo.pc.SysResourceVo;

/**
 * 主界面业务层接口
 * @author qsy
 * @version v1.0
 * @date 2017年6月14日
 */
public interface MainService {

	/**
	 * 加载菜单数据
	 * @param roleId 
	 * @return 集合
	 */
	public List<SysResourceVo> findMenu(String roleId);

}
