package com.zhengcheng.feign;

import feign.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignLoggerFactory;

/**
 * Feign INFO 日志工厂
 *
 * @author :    zhangquansheng
 * @date :    2020/4/28 14:03
 */
public class InfoFeignLoggerFactory implements FeignLoggerFactory {
    @Override
    public Logger create(Class<?> type) {
        return new InfoFeignLogger(LoggerFactory.getLogger(type));
    }
}
