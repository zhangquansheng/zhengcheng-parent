package com.zhengcheng.mvc.filter;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.zhengcheng.common.utils.MaskUtil;
import com.zhengcheng.mvc.annotation.MobileMask;
import com.zhengcheng.mvc.enumeration.MobileMaskType;

/**
 * 手机号脱敏过滤器
 *
 * @author :    quansheng.zhang
 * @Filename :     MobileContextValueFilter.java
 * @Package :     com.zhengcheng.upms.filter
 * @Description :
 * @date :    2019/1/26 7:19
 */
public class MobileContextValueFilter implements ContextValueFilter {

    @Override
    public Object process(BeanContext beanContext, Object object, String name, Object value) {
        if (beanContext == null) {
            return value;
        }
        if (value == null || !(value instanceof String)) {
            return value;
        }
        MobileMask mobileMaskAnnotation = beanContext.getAnnation(MobileMask.class);
        if (mobileMaskAnnotation != null) {
            MobileMaskType type = mobileMaskAnnotation.type();
            String propertyValue = String.valueOf(value);
            if (type == MobileMaskType.TOP7) {
                return MaskUtil.mask7Mobile(propertyValue);
            } else if (type == MobileMaskType.MIDDLE4) {
                return MaskUtil.mask4Mobile(propertyValue);
            } else {
                return MaskUtil.mask8Mobile(propertyValue);
            }
        }
        return value;
    }
}