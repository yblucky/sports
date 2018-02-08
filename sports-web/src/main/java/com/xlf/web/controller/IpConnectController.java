package com.xlf.web.controller;

import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.po.SysLogsPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.server.common.SysLogsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * @author zyc
 * ip访问
 *
 */



@RestController
@RequestMapping(value = "/ipConnect")
public class IpConnectController {
	
	@Resource
	private SysLogsService sysLogsService;
	
	
	
	@GetMapping("/findAll")
	public RespBody findAll(Paging paging){
			
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			List<SysLogsPo> list=sysLogsService.findAll(paging);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查询数据成功", list);
			//保存分页对象
			paging.setTotalCount(sysLogsService.findCount());
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查询数据失败");
			LogUtils.error("数据查找所有数据失败！",ex);
		}

		return respBody;
	}
	
	

}
