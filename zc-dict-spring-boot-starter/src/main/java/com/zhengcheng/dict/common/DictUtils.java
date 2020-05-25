package com.zhengcheng.dict.common;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

/**
 * DictCommon
 *
 * @author :    qunsheng.zhang
 * @date :    2020/5/25 20:58
 */
@Component
public class DictUtils {

    /**
     * 缓存前缀
     */
    private final String KEY_PREFIX = "zc:d:t";

    /**
     * 订阅频道
     */
    public final static String PATTERN_TOPIC = "zc:d:t:d";

    /**
     * 根据类型获取缓存key
     *
     * @param type 字典类型
     * @return KEY
     */
    public static String key(String type) {
        return StrUtil.format("zc:d:t:{}", type);
    }
}
