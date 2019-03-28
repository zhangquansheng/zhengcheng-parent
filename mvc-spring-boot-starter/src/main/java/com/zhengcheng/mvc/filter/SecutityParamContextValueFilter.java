package com.zhengcheng.mvc.filter;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.zhengcheng.common.utils.AesUtil;
import com.zhengcheng.mvc.annotation.SecurityParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 参数加解密
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.filter
 * @Description :
 * @date :    2019/3/28 10:45
 */
@Slf4j
public class SecutityParamContextValueFilter implements ContextValueFilter {

    private String aesKey;

    public SecutityParamContextValueFilter(String aesKey) {
        this.aesKey = aesKey;
    }

    @Override
    public Object process(BeanContext beanContext, Object object, String name, Object value) {
        if (beanContext == null) {
            return value;
        }
        if (value == null || !(value instanceof String)) {
            return null;
        }
        SecurityParam securityParam = beanContext.getAnnation(SecurityParam.class);
        if (securityParam != null) {
            String propertyValue = String.valueOf(value);
            try {
                if (securityParam.inDecode()) {
                    propertyValue = AesUtil.decrypt(propertyValue, aesKey);
                }
                if (securityParam.outEncode()) {
                    propertyValue = AesUtil.encrypt(propertyValue, aesKey);
                }
            } catch (Exception e) {
                log.error("SecurityParam AES code Exception! {}", e.getMessage(), e);
            }
            return propertyValue;
        } else {
            return value;
        }
    }
}
