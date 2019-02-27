package com.zhengcheng.mvc.interceptor;

import com.zhengcheng.common.web.Result;
import org.nutz.lang.Strings;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 控制层异常通知
 *
 * @author :    quansheng.zhang
 * @Filename :     ExceptionControllerAdvice.java
 * @Package :     com.zhengcheng.config
 * @Description :
 * @date :    2019/1/26 7:59
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result bindExceptionHandler(BindException e) {
        String errorMsg = e.getAllErrors().stream().map(objectError -> {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                return errorMessage(fieldError);
            }
            return objectError.getDefaultMessage();
        }).reduce((a, b) -> a + "; " + b).orElse("");
        return Result.errorMessage(errorMsg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getAllErrors().stream().map(objectError -> {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                return errorMessage(fieldError);
            }
            return objectError.getDefaultMessage();
        }).reduce((a, b) -> a + "; " + b).orElse("");
        return Result.errorMessage(errorMsg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return Result.errorMessage("表单数据填写有误：" + e.getMessage());
    }

    private String errorMessage(FieldError fieldError) {
        String message = fieldError.getDefaultMessage();
        if (Strings.equals(message, "不能为空")) {
            message = fieldError.getField() + message;
        }
        return message;
    }
}