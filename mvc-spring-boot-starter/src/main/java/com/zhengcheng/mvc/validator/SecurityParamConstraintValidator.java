package com.zhengcheng.mvc.validator;

import com.zhengcheng.mvc.annotation.SecurityParamValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义安全参数验证类
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.validator
 * @Description :
 * @date :    2019/4/4 11:40
 */
public class SecurityParamConstraintValidator implements ConstraintValidator<SecurityParamValidator, Object> {


    @Override
    public void initialize(SecurityParamValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
