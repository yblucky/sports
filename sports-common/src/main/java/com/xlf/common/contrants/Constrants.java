package com.xlf.common.contrants;

import com.xlf.common.util.ToolUtils;

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

    public static void main(String[] args) {
        Map m = new HashMap ();
        m.put (1, "1223");
        m.put (2, "123");
        List<String> list = ToolUtils.quickChoose (2, 1, m);
        List<List<String>> lists = new ArrayList<> ();
        for (int i = 0; i < list.size (); i++) {
            List innerliset = null;
            if (i % 10 == 0) {
                innerliset = new ArrayList ();
                lists.add (innerliset);
            }
            int index = 0;
            if (i > 10) {
                index = list.size () / 10;
                lists.get (index).add (list.get (i));
            } else {
                lists.get (0).add (list.get (i));
            }

        }

        System.out.println ("000000000000000000000000");
        System.out.println (lists.size ());
        for (List<String> list1 : lists) {
            System.out.println (list1.size ());
            System.out.println ("9999999999");
            for (String sss : list1) {
                System.out.println (sss);
            }
        }
    }
}
