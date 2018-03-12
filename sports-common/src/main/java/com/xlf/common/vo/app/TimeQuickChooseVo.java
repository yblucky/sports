package com.xlf.common.vo.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeQuickChooseVo {
    private Integer betType;
    private Integer multiple;
    private Integer kindType;
    private Map<Integer,String> map=new HashMap<> ();

    public Integer getBetType() {
        return betType;
    }

    public void setBetType(Integer betType) {
        this.betType = betType;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    public Integer getKindType() {
        return kindType;
    }

    public void setKindType(Integer kindType) {
        this.kindType = kindType;
    }
}
