package com.zhengcheng.common.utils;

import cn.hutool.core.util.StrUtil;

/**
 * LikeUtil
 *
 * @author quansheng1.zhang
 * @since 2021/3/12 16:34
 */
public final class LikeUtil {

    /**
     * 获取全匹配
     *
     * @param pattern 表达式
     * @return 全匹配
     */
    public static String getAll(String pattern) {
        if (StrUtil.isNotBlank(pattern)) {
            return StrUtil.format("%{}%", pattern);
        }
        return pattern;
    }

    public static String getLeft(String pattern) {
        if (StrUtil.isNotBlank(pattern)) {
            return StrUtil.format("%{}", pattern);
        }
        return pattern;
    }

    public static String getRight(String pattern) {
        if (StrUtil.isNotBlank(pattern)) {
            return StrUtil.format("{}%", pattern);
        }
        return pattern;
    }
}
