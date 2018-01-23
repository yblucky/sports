package com.xlf.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.SysParameterVo;
import com.xlf.server.web.ParameterService;

/**
 * 参数设置控制器
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
@RestController
@RequestMapping("/parameter")
public class ParameterController {
	@Resource
	private ParameterService parameterService;
	
	
	/**
	 * 查找列表数据
	 * @param paging 分页对象
	 * @return 响应信息
	 */
	@GetMapping("/findAll")
	public RespBody findAll(SysParameterVo sysParameterVo, Paging paging){
		RespBody respBody = new RespBody();
		try {
			
			
			//SysParameterVo sysParameterVo = new SysParameterVo();
			//保存返回数据
			List<SysParameterVo> list=parameterService.getInfoByTime(sysParameterVo, paging);
			
			
			
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查询参数数据成功", list);
			//保存分页对象
			paging.setTotalCount(parameterService.getInfoByTime(sysParameterVo, null).size());
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "参数设置查找所有数据失败");
			LogUtils.error("参数设置查找所有数据失败！",ex);
		}
		return respBody;
	}
	
	/**
	 * 新增参数设置
	 * @param parameterVo 新参数Vo
	 * @return 响应信息
	 */
	@PostMapping("/add")
	@SystemControllerLog(description="新增参数设置")
	public RespBody add(@RequestBody SysParameterVo parameterVo){
		RespBody respBody = new RespBody();
		try {
			parameterService.add(parameterVo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "参数设置保存成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "参数设置保存失败");
			LogUtils.error("参数设置保存失败！",ex);
		}
		return respBody;
	}
	
	/**
	 * 修改参数设置
	 * @param parameterVo 参数Vo
	 * @return 响应信息
	 */
	@PostMapping("/update")
	@SystemControllerLog(description="修改参数设置")
	public RespBody update(@RequestBody SysParameterVo parameterVo){
		RespBody respBody = new RespBody();
		try {
			parameterService.update(parameterVo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "参数设置保存成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "参数设置保存失败");
			LogUtils.error("参数设置保存失败！",ex);
		}
		return respBody;
	}
	
	/**
	 * 删除参数设置
	 * @param parameterVo 参数VO
	 * @return 响应信息
	 */
	@PostMapping("/delete")
	@SystemControllerLog(description="删除参数设置")
	public RespBody delete(@RequestBody SysParameterVo parameterVo){
		RespBody respBody = new RespBody();
		try {
			parameterService.delete(parameterVo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "参数设置删除成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "参数设置删除失败");
			LogUtils.error("参数设置删除失败！",ex);
		}
		return respBody;
	}
	
	/**
	 * 查找参数类型数据
	 *
	 * @return 响应信息
	 */
	@GetMapping("/findGrouptype")
	public RespBody findGrouptype(){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "参数类型查找所有数据成功", parameterService.findGrouptype());

		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "参数类型查找所有数据失败");
			LogUtils.error("参数类型查找所有数据失败！",ex);
		}
		return respBody;
	}


}
