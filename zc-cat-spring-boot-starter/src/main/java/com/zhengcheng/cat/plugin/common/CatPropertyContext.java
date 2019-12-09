package com.zhengcheng.cat.plugin.common;

import com.dianping.cat.Cat;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/9 20:30
 */
public class CatPropertyContext implements Cat.Context {

    private Map<String, String> properties = new HashMap();


    @Override
    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    @Override
    public String getProperty(String key) {
        return (String) this.properties.get(key);
    }
}

