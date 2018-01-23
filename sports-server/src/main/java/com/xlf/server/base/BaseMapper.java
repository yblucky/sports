package com.xlf.server.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用dao基础接口，其他dao继承该接口即可
 * @author qsy
 * @version v1.0
 * @date 2016年11月26日
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
