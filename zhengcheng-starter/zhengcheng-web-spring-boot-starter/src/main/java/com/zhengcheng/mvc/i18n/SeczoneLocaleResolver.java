package com.zhengcheng.mvc.i18n;

import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义语言解析器
 *
 * @author quansheng1.zhang
 * @since 2024/2/1 18:16
 */
public class SeczoneLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (request.getHeader("i18n") != null) {
            String[] split = request.getHeader("i18n").split("_");
            if (split.length > 1) {
                return new Locale(split[0], split[1]);
            }
        }

        if (request.getHeader("Accept-Language") != null) {
            String acceptLanguage = request.getHeader("Accept-Language").split(",")[0];
            String[] split = acceptLanguage.split("_");
            if (split.length > 1) {
                return new Locale(split[0], split[1]);
            }
        }
        return Locale.getDefault();
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
