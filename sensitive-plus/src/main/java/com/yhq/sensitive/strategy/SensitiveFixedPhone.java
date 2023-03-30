package com.yhq.sensitive.strategy;


import com.yhq.sensitive.enums.SensitiveDefaultLengthEnum;
import com.yhq.sensitive.util.SensitiveInfoUtils;

/**
 * 固话脱敏
 * @author yhq
 * @date 2021年9月6日 16点13分
 **/
public class SensitiveFixedPhone implements IStrategy {

    @Override
    public String desensitization(String fixedPhone,int begin ,int end) {
        if(begin != SensitiveDefaultLengthEnum.FIXED_PHONE.getBegin() && begin !=0 &&
                end != SensitiveDefaultLengthEnum.FIXED_PHONE.getEnd() && end !=0){
            return SensitiveInfoUtils.fixedPhone(fixedPhone,end);
        }
        return SensitiveInfoUtils.fixedPhone(fixedPhone, SensitiveDefaultLengthEnum.FIXED_PHONE.getEnd());
    }

}
