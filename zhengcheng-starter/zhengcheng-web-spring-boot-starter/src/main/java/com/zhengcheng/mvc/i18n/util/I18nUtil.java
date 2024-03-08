package com.zhengcheng.mvc.i18n.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * I18nUtil
 *
 * @author quansheng1.zhang
 * @since 2024/2/1 18:20
 */
@Slf4j
public class I18nUtil {

    public static String get(String key) {
        return get(key, new String[0]);
    }

    public static String getLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }

    public static String get(String key, Object... args) {
        try {
            return getInstance().getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            log.warn("I18nUtil get exception. {}", e.getMessage());
            return key;
        }
    }

    private static MessageSource getInstance() {
        return Lazy.messageSource;
    }

    /**
     * 使用懒加载方式实例化messageSource国际化工具
     */
    private static class Lazy {
        private static final MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
    }

}
