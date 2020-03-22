package com.zhengcheng.web.filter;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.zhengcheng.web.annotation.MobileMask;
import com.zhengcheng.web.utils.Mask;

import java.util.Objects;

/**
 * 手机号脱敏过滤器
 *
 * @author :    quansheng.zhang
 * @date :    2019/1/26 7:19
 */
public class MobileContextValueFilter implements ContextValueFilter {

    private String mobileMaskType;

    public MobileContextValueFilter(String mobileMaskType) {
        this.mobileMaskType = mobileMaskType;
    }

    @Override
    public Object process(BeanContext beanContext, Object object, String name, Object value) {
        if (beanContext == null) {
            return value;
        }
        if (value == null || !(value instanceof String)) {
            return value;
        }
        MobileMask mobileMaskAnnation = beanContext.getAnnation(MobileMask.class);
        if (mobileMaskAnnation != null) {
            String propertyValue = String.valueOf(value);
            if (Objects.equals(this.mobileMaskType, "middle4")) {
                propertyValue = Mask.mask4Mobile(propertyValue);
            } else if (Objects.equals(this.mobileMaskType, "top7")) {
                propertyValue = Mask.mask7Mobile(propertyValue);
            } else if (Objects.equals(this.mobileMaskType, "bottom8")) {
                propertyValue = Mask.mask8Mobile(propertyValue);
            }
            return propertyValue;
        } else {
            return value;
        }
    }
}
