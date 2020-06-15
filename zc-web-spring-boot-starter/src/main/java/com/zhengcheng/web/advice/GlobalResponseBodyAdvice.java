package com.zhengcheng.web.advice;

import cn.hutool.json.JSONUtil;
import com.zhengcheng.common.web.PageResult;
import com.zhengcheng.common.web.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * com.zhengcheng 下 ResponseBodyAdvice 统一返回结果处理
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@RestControllerAdvice(
        basePackages = {"com.zhengcheng"}
)
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result || body instanceof PageResult) {
            return body;
        }
        Result result = Result.successData(body);
        if (body instanceof String) {
            return JSONUtil.toJsonStr(result);
        }
        return result;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 此处可通过returnType.getDeclaringClass()  returnType.getMethod().getName() 过滤不想拦截的类或者方法。
        return true;
    }
}