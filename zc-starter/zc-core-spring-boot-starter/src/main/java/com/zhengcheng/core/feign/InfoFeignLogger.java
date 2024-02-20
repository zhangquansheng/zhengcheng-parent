package com.zhengcheng.core.feign;


/**
 * Feign INFO 日志
 *
 * @author :    zhangquansheng
 * @date :    2020/4/28 14:04
 */
public class InfoFeignLogger extends feign.Logger {

    // 建议使用slf4j这样项目在更换日志框架也不用修改源代码了，扩展性更强
    private final org.slf4j.Logger logger;

    public InfoFeignLogger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(String.format(methodTag(configKey) + format, args));
        }
    }
}
