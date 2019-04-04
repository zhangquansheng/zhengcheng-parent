package com.zhengcheng.mvc.annotation;

import com.zhengcheng.mvc.validator.SecurityParamConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 参数解密验证
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.annotation
 * @Description :
 * @date :    2019/4/4 11:38
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = SecurityParamConstraintValidator.class)
public @interface SecurityParamValidator {

    String message() default "参数解密失败";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
