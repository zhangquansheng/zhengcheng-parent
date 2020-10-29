package com.zhengcheng.core.socketio.properties;

import lombok.Data;

/**
 * RedissonProperties
 *
 * @author :    zhangquansheng
 * @date :    2019/12/27 16:04
 */
@Data
public class RedissonProperties {
    private boolean enable;
    private String host;
    private String port;
    private String password;
}
