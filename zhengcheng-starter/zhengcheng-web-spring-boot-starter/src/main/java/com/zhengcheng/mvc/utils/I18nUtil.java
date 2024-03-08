package com.zhengcheng.mvc.utils;

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

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args) {
        try {
            MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
            return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception ignored) {
            return code;
        }
    }

}
