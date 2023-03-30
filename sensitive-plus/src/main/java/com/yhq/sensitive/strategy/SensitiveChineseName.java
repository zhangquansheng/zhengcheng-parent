package com.yhq.sensitive.strategy;

import com.yhq.sensitive.enums.SensitiveDefaultLengthEnum;
import com.yhq.sensitive.util.SensitiveInfoUtils;

/**
 * 中文名称脱敏
 * @author yhq
 * @date 2021年9月6日 16点13分
 **/
public class SensitiveChineseName implements IStrategy {

    @Override
    public String desensitization(String source,int begin,int end) {
        if(begin != SensitiveDefaultLengthEnum.CHINESE_NAME.getBegin() && begin !=0){
            return SensitiveInfoUtils.chineseName(source,begin);
        }
        return SensitiveInfoUtils.chineseName(source, SensitiveDefaultLengthEnum.CHINESE_NAME.getBegin());
    }

}
