package com.zhengcheng.mvc.filter;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.zhengcheng.mvc.annotation.EncryptId;
import com.zhengcheng.common.utils.HttpAesUtil;

/**
 * ID加密、解密过滤器
 *
 * @author :    quansheng.zhang
 * @Filename :     IDEncryptContextValueFilter.java
 * @Package :     com.zhengcheng.mvc.filter
 * @Description :
 * @date :    2019/1/31 22:09
 */
public class EncryptIdContextValueFilter implements ContextValueFilter {

    private String key;
    private String iv;

    public EncryptIdContextValueFilter(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }

    @Override
    public Object process(BeanContext beanContext, Object object, String name, Object value) {
        if (value == null || !(value instanceof Long)) {
            return value;
        }
        EncryptId encryptIdAnnotation = beanContext.getAnnation(EncryptId.class);
        if (encryptIdAnnotation != null) {
            String propertyValue = String.valueOf(value);
            return HttpAesUtil.encrypt(propertyValue, key, false, iv);
        }
        return value;
    }
}
