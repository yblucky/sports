package com.xlf.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.io.SAXReader;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *    创建时间：2017年3月30日  版本号：v1.0
 **/
public class XMLUtil {
    public static Map doXMLParse(String strxml) {
        Map m = null;
        InputStream in = null;
        try {
            if (null == strxml || "".equals(strxml)) {
                return null;
            }
            System.out.println("11111111111111111111");
            System.out.println(strxml);
            strxml = strxml.replaceFirst("encoding=\"gb2312\"", "encoding=\"UTF-8\"");
            System.out.println("0000000000000000000");
            System.out.println(strxml);
            m = new HashMap();
            in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String k = e.getName();
                String v = "";
                List children = e.getChildren();
                if (children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = XMLUtil.getChildrenText(children);
                }
                m.put(k, v);

            }
            // 关闭流
            in.close();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        return sb.toString();
    }


    /**
     * XML 转换成map
     **/
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        try {
            InputStream ins = request.getInputStream();

            org.dom4j.Document doc = reader.read(ins);
            org.dom4j.Element root = doc.getRootElement();

            @SuppressWarnings("unchecked")
            List<org.dom4j.Element> list = root.elements();
            for (org.dom4j.Element e : list) {
                map.put(e.getName(), e.getText());
            }
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将对象转化为xml
     *
     * @param o
     * @return
     */
    public static String objectToXml(Object o) {
//        XStream xstream = new XStream();
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.alias("xml", o.getClass());
        String xml = xStream.toXML(o);
        xml.replaceAll("__", "_");
        return xml;
    }


    public static void main(String[] args) throws JDOMException, IOException {
    /*String xml="<xml>"
                + "<appid><![CDATA[wx2421b1c4370ec43b]]></appid>"
				+ "<attach><![CDATA[eyJpZCI6IkRBNkI3QzE1Mjc5RjQ1NEY4ODk1OUFDRDc3NThBMTQxIiwidGltZSI6MTQ5MDkyNDYzODE2MSwidXNlck5hbWUiOiLmuIXmtYUiLCJuaWNrTmFtZSI6Iua4hea1hSJ9]]></attach>"
				+ "<bank_type><![CDATA[CFT]]></bank_type>"
				+ "<fee_type><![CDATA[CNY]]></fee_type>"
				+ "<is_subscribe><![CDATA[Y]]></is_subscribe>"
				+ "<mch_id><![CDATA[10000100]]></mch_id>"
				+ "<nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>"
				+ "<openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>"
				+ "<out_trade_no><![CDATA[20170331112844862001]]></out_trade_no>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>"
				+ "<sub_mch_id><![CDATA[10000100]]></sub_mch_id>"
				+ "<time_end><![CDATA[20140903131540]]></time_end>"
				+ "<total_fee>1</total_fee>"
				+ "<trade_type><![CDATA[JSAPI]]></trade_type>"
				+ "<transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>"
				+ "</xml>";
    		Map<String, String> map = XMLUtil.doXMLParse(xml);
    		System.out.println(JSON.toJSON(map));
    		String requestUrl="http://192.168.2.222:8090/api/wallet/recharge/wxCallback";
    		HttpUtil.doPostRequest(requestUrl, xml);*/

    }
}
