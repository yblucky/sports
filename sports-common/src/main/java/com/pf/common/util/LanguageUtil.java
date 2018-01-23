package com.pf.common.util;

import com.pf.common.enums.LanguageEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 多语言工具类
 *
 * @author jay.zheng
 * @version V1.0
 * @date 2017年9月20日
 */
@Component
public class LanguageUtil {

    @Resource
    private MessageSource messageSource;
    @Resource
    private HttpServletRequest request;

    /**
     * 读取带参数的多语言提示信息
     *
     * @param field  要提示的字段
     * @param params 要替换的参数，按顺序
     */
    public String getMessage(String field, Object[] params) {
        Locale locale = Locale.CHINA;
        String language = request.getHeader("requestLanguage");
        if (StringUtils.isEmpty(language)) {
            language = request.getParameter("requestLanguage");
        }
        if (LanguageEnum.EN_US.getCode().equals(language)) {
            locale = Locale.US;
        }
        String msg = "";
        try {
            msg = messageSource.getMessage(field, params, locale);
        } catch (Exception ex) {
            LogUtils.error("获取多语言失败！！", ex);
        }
        return msg;
    }

    /**
     * 读取国际语言提示信息
     *
     * @param field  显示国际信息对应的key值
     * @param defaul 默认显示的信息
     */
    public String getMsg(String field, String defaul) {
        String msg = this.getMessage(field, null);
        if (StringUtils.isEmpty(msg)) {
            msg = defaul;
        }
        return msg;
    }

    /**
     * 返回国际语言的语种
     *
     */
    public String getLanguage() {
        String language = request.getHeader("requestLanguage");
        if (StringUtils.isEmpty(language)) {
            language = request.getParameter("requestLanguage");
        }
        if (StringUtils.isEmpty(language)) {
            language = LanguageEnum.ZH_CN.getCode();
        }
        return language;
    }
}
