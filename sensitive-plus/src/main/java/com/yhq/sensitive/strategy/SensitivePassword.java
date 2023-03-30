package com.yhq.sensitive.strategy;

import com.yhq.sensitive.enums.SensitiveDefaultLengthEnum;
import com.yhq.sensitive.util.SensitiveInfoUtils;

/**
 * 密码脱敏
 * @author yhq
 * @date 2021年9月6日 16点13分
 **/
public class SensitivePassword implements IStrategy {

    @Override
    public String desensitization(String password,int begin,int end) {
        if(begin != SensitiveDefaultLengthEnum.PASSWORD.getBegin() && begin !=0){
            return SensitiveInfoUtils.password(password,begin);
        }
        return SensitiveInfoUtils.password(password, SensitiveDefaultLengthEnum.PASSWORD.getBegin());
    }

}
