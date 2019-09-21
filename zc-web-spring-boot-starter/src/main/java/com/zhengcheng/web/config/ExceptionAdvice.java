package com.zhengcheng.web.config;

import com.zhengcheng.web.interceptor.DefaultExceptionAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 统一异常处理
 *
 * @author zlt
 * @date 2018/12/22
 */
@ControllerAdvice
public class ExceptionAdvice extends DefaultExceptionAdvice {
}
