package com.pf.server.mapper;

import com.pf.common.po.AppBankTypePo;
import com.pf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 银行卡类型DAO接口
 *
 * @author lxl
 * @version v1.0
 * @date 2017年6月14日
 */
@Repository
public interface AppBankTypeMapper extends BaseMapper<AppBankTypePo> {
	 @Select("SELECT * FROM app_bank_type")
	 public List<AppBankTypePo> getall();
}
