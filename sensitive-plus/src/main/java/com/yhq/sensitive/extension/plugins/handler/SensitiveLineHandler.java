package com.yhq.sensitive.extension.plugins.handler;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * SensitiveLineHandler
 *
 * @author quansheng1.zhang
 * @since 2023/4/1 9:52
 */
public interface SensitiveLineHandler {
    /**
     * 是否脱敏
     */
    default boolean isSensitive(JsonGenerator gen) {
        return true;
    }
}
