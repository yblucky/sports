/* 
 * 文件名：UserService.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月12日
 * 版本号：v1.0
*/
package com.pf.server.web;

import java.util.List;

import com.pf.common.po.UseImagePo;

/**
 * 用户业务层接口
 * @author yyr
 * @version v1.0
 * @date 2017年6月12日
 */
public interface UseImageService {
	List<UseImagePo> getByIds(List<String> ids);
}
