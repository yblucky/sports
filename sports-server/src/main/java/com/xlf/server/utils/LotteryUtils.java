package com.xlf.server.utils;

import com.xlf.common.contrants.Constrants;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.BettingBaseVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotteryUtils {
    //组装北京赛车的countMap
    public static Map<String, Map<String, Integer>>  makeRaceingCountMapWithAlllist(List<BettingBaseVo> allList) {
        String keyPrefix = Constrants.REG_RACING_PREFIX;
        Map<String, Map<String, Integer>> countMap = new HashMap<>();
        for (int i = 1; i < 11; i++) {
            countMap.put(keyPrefix+i,new HashMap<String,Integer>());
        }
        for (BettingBaseVo bettingBaseVo : allList) {
            for (int i = 1; i < 11; i++) {
                String key = keyPrefix + i;
                if (ToolUtils.regex(bettingBaseVo.getBettingContent(), key)) {
                    if (countMap.containsKey(key) && !countMap.get(key).containsKey(bettingBaseVo.getBettingContent())) {
                        countMap.get(key).put(bettingBaseVo.getBettingContent(), bettingBaseVo.getMultiple());
                    } else {
                        Integer already = countMap.get(key).get(bettingBaseVo.getBettingContent()).intValue();
                        countMap.get(key).put(bettingBaseVo.getBettingContent(), already + bettingBaseVo.getMultiple());
                    }
                }
            }
        }
        return countMap;
    }


    //计算北京赛车中奖的最大注数
    public static Integer oneRacingSumMaxMutiple(Map<String, Map<String, Integer>> countMap) {
        System.out.println("*********************************");
        System.out.println(ToolUtils.toJson(countMap));
        System.out.println("*********************************");
        Integer sumBettingNo = 0;
        String key = "";
        for (int i = 0; i < 11; i++) {
            key = Constrants.REG_RACING_PREFIX + i;
            if (countMap.containsKey(key) && countMap.get(key).size() > 0) {
                Integer mutiple = ToolUtils.compareMapList(countMap.get(key)).get(0).getValue();
                sumBettingNo += mutiple;
            }
        }
        return sumBettingNo;
    }
}
