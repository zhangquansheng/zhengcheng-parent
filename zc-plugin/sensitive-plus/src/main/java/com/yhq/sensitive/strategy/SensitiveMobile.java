package com.yhq.sensitive.strategy;

import com.yhq.sensitive.enums.SensitiveDefaultLengthEnum;
import com.yhq.sensitive.util.SensitiveInfoUtils;

/**
 * 手机号码脱敏
 * @author yhq
 * @date 2021年9月6日 16点13分
 **/
public class SensitiveMobile implements IStrategy {

    @Override
    public String desensitization(String mobile,int begin ,int end) {
        if(begin != SensitiveDefaultLengthEnum.MOBILE.getBegin() && begin !=0 &&
                end != SensitiveDefaultLengthEnum.MOBILE.getEnd() && end !=0){
            return SensitiveInfoUtils.mobilePhone(mobile,begin,end);
        }
        return SensitiveInfoUtils.mobilePhone(mobile, SensitiveDefaultLengthEnum.MOBILE.getBegin(), SensitiveDefaultLengthEnum.MOBILE.getEnd());
    }

}
