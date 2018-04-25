package com.xlf.common.contrants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/2 0002.
 */
public class Constrants {
    public static final String REG_TIME_ONE = "\\dXXXX";
    public static final String REG_TIME_TWO = "X\\dXXX";
    public static final String REG_TIME_THREE = "XX\\dXX";
    public static final String REG_TIME_FOURE = "XXX\\dX";
    public static final String REG_TIME_FIVE = "XXXX\\d";


    public static final String REG_TIME_ONE_DOUBLE = "XX\\dX\\d";
    public static final String REG_TIME_TWO_DOUBLE = "X\\dX\\dX";
    public static final String REG_TIME_THREE_DOUBLE = "\\dXXX\\d";
    public static final String REG_TIME_FOURE_DOUBLE = "X\\d\\dXX";
    public static final String REG_TIME_FIVE_DOUBLE = "\\dX\\dXX";
    public static final String REG_TIME_SIX_DOUBLE = "XX\\d\\dX";
    public static final String REG_TIME_SEVEN_DOUBLE = "XXX\\d\\d";
    public static final String REG_TIME_EIGHT_DOUBLE = "X\\dXX\\d";
    public static final String REG_TIME_NINE_DOUBLE = "\\d\\dXXX";
    public static final String REG_TIME_TEN_DOUBLE = "\\dXX\\dX";
    public static final List<String> racingRegexList = new ArrayList<String>() {
        {
            add(0, "\\dXXXXXXXXX");
            add(1, "X\\dXXXXXXXX");
            add(2, "XX\\dXXXXXXX");
            add(3, "XXX\\dXXXXXX");
            add(4, "XXXX\\dXXXXX");
            add(5, "XXXXX\\dXXXX");
            add(6, "XXXXXX\\dXXX");
            add(7, "XXXXXXX\\dXX");
            add(8, "XXXXXXXX\\dX");
            add(9, "XXXXXXXXX\\d");
        }
    };
    public static final String REG_RACING_PREFIX="REG_RACING_";
    public static final String REG_RACING_1 = "\\dXXXXXXXXX";
    public static final String REG_RACING_2 = "X\\dXXXXXXXX";
    public static final String REG_RACING_3 = "XX\\dXXXXXXX";
    public static final String REG_RACING_4 = "XXX\\dXXXXXX";
    public static final String REG_RACING_5 = "XXXX\\dXXXXX";
    public static final String REG_RACING_6 = "XXXXX\\dXXXX";
    public static final String REG_RACING_7 = "XXXXXX\\dXXX";
    public static final String REG_RACING_8 = "XXXXXXX\\dXX";
    public static final String REG_RACING_9 = "XXXXXXXX\\dX";
    public static final String REG_RACING_10 = "XXXXXXXXX\\d";
    public static final Map<String, String> racingRegexDescMap = new HashMap<String, String>() {
        {
            put("\\dXXXXXXXXX", "1赛道");
            put("X\\dXXXXXXXX", "2赛道");
            put("XX\\dXXXXXXX", "3赛道");
            put("XXX\\dXXXXXX", "4赛道");
            put("XXXX\\dXXXXX", "5赛道");
            put("XXXXX\\dXXXX", "6赛道");
            put("XXXXXX\\dXXX", "7赛道");
            put("XXXXXXX\\dXX", "8赛道");
            put("XXXXXXXX\\dX", "9赛道");
            put("XXXXXXXXX\\d", "10赛道");
        }
    };
}
