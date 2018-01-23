package com.xlf.server.mapper;

import com.xlf.common.po.AppBankCardPo;
import com.xlf.common.vo.app.BankCardVo;
import com.xlf.common.vo.pc.AppBankCardVo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 银行卡DAO接口
 *
 * @author lxl
 * @version v1.0
 * @date 2017年6月14日
 */
public interface AppBankCardMapper extends BaseMapper<AppBankCardPo> {

    /**
     * 更新用户的银行卡变成不默认
     *
     * @param userId
     * @return
     */
    @Update("update app_bank_card set defaultState = 20 where userId= #{userId}")
    public int updUserDefualt(@Param("userId") String userId);
    
    /**
     * 查询银行卡列表
     * @return
     */
    public List<AppBankCardVo> getPoList(@Param("model") AppBankCardVo vo, @Param("startRow") int startRow, @Param("pageSize") int pageSize);
    
    /**
     * 查询银行卡信息
     * @return 银行卡信息
     */
    @Select("select abc.*,abt.bankName from app_bank_card abc,app_bank_type abt where abt.id=abc.bankTypeId and abc.id=#{id}")
	public BankCardVo findKey(@Param("id") String id);
    
    /**
     * 根据id更新银行信息
     * @return
     */
    @Update("update app_bank_card set name = #{model.name} where userId= #{model.userId}")
    public int update(@Param("model") BankCardVo bankCardVo);
}
