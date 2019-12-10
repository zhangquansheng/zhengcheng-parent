package com.zhengcheng.cat.context;

import com.dianping.cat.Cat;

import java.util.HashMap;
import java.util.Map;

/**
 * CatMsgContext
 *
 * @author :    zhangquansheng
 * @date :    2019/12/10 11:23
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
