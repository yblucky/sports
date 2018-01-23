package com.xlf.common.util;


import com.xlf.common.po.AppPerformanceParamPo;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


/**
 * Created by summer on 2016-12-02:09:32;
 */
public class AutoGenerMapper {

    public static void main(String[] args) {
        genMapperXml(AppPerformanceParamPo.class);
    }

    public static void genMapperXml(Class clz) {
        String model = clz.getSimpleName();
        String fullModelName = clz.getName();
        String mapperName = "";
        String tableName = camelToUnderline(model).replaceFirst("_", "");
        String modelName = model;
        Field[] af = null;
        Field[] fields = clz.getDeclaredFields();
        Field[] fields2 = clz.getSuperclass().getDeclaredFields();
        af = (Field[]) ArrayUtils.addAll(fields, fields2);
        Field[] fields3 = clz.getSuperclass().getSuperclass().getDeclaredFields();
        Field[] fields4 = null;
        if (fields3 != null) {
//            fields4 = clz.getSuperclass().getSuperclass().getSuperclass().getDeclaredFields();
        }
        af = (Field[]) ArrayUtils.addAll(af, fields3);
        af = (Field[]) ArrayUtils.addAll(af, fields4);
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        StringBuffer sb4 = new StringBuffer();
        StringBuffer sb5 = new StringBuffer();
        StringBuffer condition = new StringBuffer();
        int andIndex = 0;
        int idCount = 0;
        for (Field f : af) {
            boolean isStatic = Modifier.isStatic(f.getModifiers());
            if (isStatic) continue;
            if (idCount == 0) {
                sb2.append(",").append("#{id}");
                idCount++;
            }
            if (f.getName().equals("serialVersionUID") || f.getName().equals("id")) continue;
            if (!f.getName().equals("deleteTime")) {
//                sb.append(",").append(camelToUnderline(f.getName()));
                sb.append(",").append(f.getName());
            }
            if (f.getName().equals("createTime") || f.getName().equals("updateTime")) {
                sb2.append(",").append("now()");
            }
//            else if (f.getName().equals("status")) {
//                sb2.append(",").append("1");
//            }
            else if (f.getName().equals("deleteTime")) {
                //sb2.append(",").append("'1970-01-01 00:00:00'");
            } else {
                sb2.append(",").append("#{").append(f.getName()).append("}");
            }

            if (f.getName().equals("createTime") || f.getName().equals("updateTime") || f.getName().equals("deleteTime")) {
                continue;
            }
            String and = "and ";
            if (andIndex == 0) {
                and = "";
            }
            if (f.getType().getName().equals("java.lang.String")) {
                sb3.append("\r\n\t\t\t<if test=\"model.").append(f.getName()).append(" != null and model.").append(f.getName()).append(" != ''\">").append(and).append(f.getName())
                        .append("=#{model.").append(f.getName()).append("}</if>");
                sb5.append("\r\n\t\t\t<if test=\"model." + f.getName() + " != null" + " and " + "model." + f.getName() + " !=''\">" + f.getName() + "= #{model." + f.getName() + "},</if>");

            } else {
                sb3.append("\r\n\t\t\t<if test=\"model.").append(f.getName()).append(" != null\">").append(and).append(f.getName())
                        .append("=#{model.").append(f.getName()).append("}</if>");
                sb5.append("\r\n\t\t\t<if test=\"model." + f.getName() + " != null\">" + f.getName() + "= #{model." + f.getName() + "},</if>");
            }


            sb4.append("\r\n\t\t\t<if test=\"").append(f.getName()).append(" != null\">").append(and).append(f.getName())
                    .append("=#{").append(f.getName()).append("}</if>");

            andIndex++;


        }

        String[] fieldList= sb.toString().split(",");
        StringBuffer keys=new StringBuffer();
        StringBuffer values=new StringBuffer();
        keys.append("\t\n\t\t<trim  suffixOverrides=\",\" >");
        values.append("\t\n\t\t<trim  suffixOverrides=\",\" >\t\n\t\t");
        for(String str:fieldList){
            if("".equals(str)){
                keys.append("\t\n\t\t\t<if test=\"model.id!= null  and model.id!=''\">\t\n\t\t\t id,\t\n\t\t\t</if>");
                values.append("\t\n\t\t\t<if test=\"model.id!= null  and model.id!=''\">\t\n\t\t\t#{model.id},\t\n\t\t\t</if>");
            }else {
                keys.append("\t\n\t\t\t<if test=\"model." + str + "!= null  and model." + str + "!=''\">\t\n\t\t\t" +
                        str +
                        ",\t\n\t\t\t</if>");
                values.append("\t\n\t\t\t<if test=\"model." + str + "!= null  and model." + str + "!=''\">\t\n\t\t\t#{model." +
                        str +
                        "},\t\n\t\t\t</if>");
            }
        }
        keys.append("\t\n\t\t</trim>\t\n\t\t");
        values.append("\t\n\t\t</trim> \t\n\t\t");

        condition.append("\t<sql id=\"condition\">\r\n").append("\t\t<where>\t\t\t").append(sb3).append("\r\n\t\t</where>\r\n\t</sql>");

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        xml.append("\r\n");
        xml.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >");
        xml.append("\r\n");
        xml.append("<mapper namespace=\"" + mapperName + "\">");
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append("\t<sql id=\"fields\">\r\n\t\t" + sb.toString().replaceFirst(",", "") + "\r\n\t</sql>");
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append("\t<sql id=\"fields_id\">\r\n\t\tid,<include refid=\"fields\"/>\r\n\t</sql>");
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append("\t<sql id=\"table_name\">" + tableName + "</sql>");
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append("\t<sql id=\"selector\">\r\n\t\tselect <include refid=\"fields_id\"/> from <include refid=\"table_name\"/>\r\n\t</sql>");
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append(condition.toString());
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append("\t<insert id=\"create\" useGeneratedKeys=\"true\" keyProperty=\"id\">\r\n\t\tinsert into <include refid=\"table_name\"/>("+keys+")\r\n\t\tvalues(" + values + ");\r\n\t</insert>");
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append("\t<select id=\"readById\" resultType=\"" + fullModelName + "\">\r\n\t\t<include refid=\"selector\"/> where id=#{id}  limit 1;\r\n\t</select>");
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append("\t<select id=\"readList\" resultType=\"" + fullModelName + "\">\r\n\t\t<include refid=\"selector\"/> <include refid=\"condition\"/> limit #{startRow}, #{pageSize};\r\n\t</select>");
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append("\t<select id=\"readOne\" resultType=\"" + fullModelName + "\">\r\n\t\t<include refid=\"selector\"/> <include refid=\"condition\"/> limit 1;\r\n\t</select>");
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append("\t<select id=\"readCount\" resultType=\"int\">\r\n\t\tselect count(1) from <include refid=\"table_name\"/> <include refid=\"condition\"/> \r\n\t</select>");
        xml.append("\r\n");
        xml.append("\r\n");

        xml.append("\t<update id=\"updateById\">\r\n\t\tupdate <include refid=\"table_name\"/> \r\n\t\t<set>\r\n" + sb5.toString().replaceFirst("\r\n", "") + "\r\n\t\t\t" + "updateTime=now()\r\n\t\t</set>\r\n\t\twhere id=#{id}\r\n\t</update>");
        xml.append("\r\n");
        xml.append("\r\n");
        xml.append("\t<update id=\"deleteById\">\r\n\t\tupdate <include refid=\"table_name\"/> set status = 0,updateTime=now() where id = #{id}\r\n\t</update>");
        xml.append("\r\n");
        xml.append("</mapper>");
        xml.append("\r\n");

        System.out.println(xml.toString());
    }

    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
