package com.zhengcheng.cat.plugin.common;

import com.dianping.cat.Cat;

import java.util.HashMap;
import java.util.Map;

/**
 * CatMsgContext
 *
 * @author :    zhangquansheng
 * @date :    2019/12/10 9:19
 */
public class CatMsgContext implements Cat.Context {

    private Map<String, String> properties = new HashMap<>();

    @Override
    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }
}
