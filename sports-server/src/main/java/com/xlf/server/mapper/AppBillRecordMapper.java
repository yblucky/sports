package com.xlf.server.mapper;

import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.vo.app.AppBillRecordVo;
import com.xlf.common.vo.pc.LotteryVo;
import com.xlf.common.vo.pc.RevenueVo;
import com.xlf.common.vo.pc.WebBillRecordVo;
import com.xlf.common.vo.pc.WebStatisticsVo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流水对账DAO接口
 *
 * @author lxl
 * @version v1.0
 * @date 2017年6月14日
 */
@Repository
public interface AppBillRecordMapper extends BaseMapper<AppBillRecordPo> {

    /**
     * 查询流水
     *
     * @param busnessTypeList
     * @param rowBounds
     * @return
     */
    List<AppBillRecordVo> findBillRecordList(@Param("userId") String userId, @Param("list") List<Integer> busnessTypeList, @Param("startTime")String startTime,@Param("endTime")String endTime,RowBounds rowBounds);

    /**
     * 查询流水
     *
     * @param busnessTypeList
     * @return
     */
    Integer billRecordListTotal(@Param("userId") String userId, @Param("list") List<Integer> busnessTypeList,@Param("startTime")String startTime,@Param("endTime")String endTime);


    List<AppBillRecordVo> findBillRecord(@Param("model") AppBillRecordPo po, RowBounds rowBounds);

    /**
     * 获取所有的货币类型
     *
     * @return
     */
    @Select("select currencyType from app_bill_record group by currencyType")
    List<String> getCurrencyType();


    /**
     * 获取所有的业务类型
     *
     * @return
     */
    @Select("select busnessType from app_bill_record group by busnessType")
    List<String> getBusnessType();


    List<WebBillRecordVo> findAll(@Param("model") WebBillRecordVo vo, @Param("startRow") int startRow, @Param("pageSize") int pageSize);


    Integer findCount(@Param("model") WebBillRecordVo vo);

    /**
     * 查询当天提现笔数
     *
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(id) FROM `app_bill_record` where userId=#{userId} and to_days(createTime) = to_days(now())")
    Integer countCurrentDayWithDraw(@Param("userId") String userId);


    @Select("select sum(balance) as balance,busnessType from app_bill_record GROUP BY busnessType")
    List<WebStatisticsVo> SUMCount();

    Integer batchSaveKickBackAmoutRecord(@Param("list") List<AppBillRecordPo> list);

    List<RevenueVo> revenueList(@Param("model") RevenueVo vo, RowBounds rowBounds);

    @Select("select sum(balance) as balance,busnessType from app_bill_record where datediff(now(),createTime)=0 GROUP BY busnessType")
    List<WebStatisticsVo> selectSumByBusnessType();

    /**
     * 查询报表数据
     *
     * @param busnessTypeList
     * @return
     */
    Double report(@Param("userId") String userId, @Param("list") List<Integer> busnessTypeList, @Param("startTime") String startTime, @Param("endTime") String endTime);


    Integer reportCount(@Param("userId") String userId, @Param("list") List<Integer> busnessTypeList, @Param("startTime") String startTime, @Param("endTime") String endTime);


    List<LotteryVo> findBetRecord(@Param("model") LotteryVo vo,RowBounds rowBounds);

    Integer findBetRecordCount(@Param("model") LotteryVo vo);


    long revenueCount(@Param("model")RevenueVo vo);
}
