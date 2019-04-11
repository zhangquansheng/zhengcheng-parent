package com.zhengcheng.mvc.client;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zhengcheng.common.constant.CommonConstant;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.http.HttpWrapper;
import com.zhengcheng.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RpcClient
 *
 * @author :    quansheng.zhang
 * @Filename :     RpcClient.java
 * @Package :     com.zhangmen.todo.mvc.client
 * @Description :
 * @date :    2019/4/3 16:50
 */
@Slf4j
@Component
public class RpcClient {

    private final String CODE = "code";

    private final String DATA = "data";

    private final String MESSAGE = "message";


    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = {IllegalArgumentException.class, BizException.class})
    public <T> Result<T> get(String url, Class<T> clazz) {
        String resp = this.get(url, new HashMap<>(4));
        return this.getResult(resp, clazz);
    }

    public <T> Result<T> fallback(String url, Class<T> clazz, Throwable throwable) {
        log.error("fallback#{}，进入回退方法，异常：", url, throwable);
        return this.fallbackResult();
    }

    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = {IllegalArgumentException.class, BizException.class})
    public <T> Result<T> get(String url, Map<String, Object> param, Class<T> clazz) {
        String resp = this.get(url, param);
        return this.getResult(resp, clazz);
    }

    public <T> Result<T> fallback(String url, Map<String, Object> param, Class<T> clazz, Throwable throwable) {
        log.error("fallback#{}，进入回退方法，异常：", url, throwable);
        return this.fallbackResult();
    }

    @HystrixCommand(fallbackMethod = "fallbackList", ignoreExceptions = {IllegalArgumentException.class, BizException.class})
    public <T> Result<List<T>> getList(String url, Class<T> clazz) {
        String resp = this.get(url, new HashMap<>(4));
        return this.getResult0(resp, clazz);
    }

    public <T> Result<List<T>> fallbackList(String url, Class<T> clazz, Throwable throwable) {
        log.error("fallbackList#{}，进入回退方法，异常：", url, throwable);
        return this.fallbackResult();
    }

    @HystrixCommand(fallbackMethod = "fallbackList", ignoreExceptions = {IllegalArgumentException.class, BizException.class})
    public <T> Result<List<T>> getList(String url, Map<String, Object> param, Class<T> clazz) {
        String resp = this.get(url, param);
        return this.getResult0(resp, clazz);
    }

    public <T> Result<List<T>> fallbackList(String url, Map<String, Object> param, Class<T> clazz, Throwable throwable) {
        log.error("fallbackList#{}，进入回退方法，异常：", url, throwable);
        return this.fallbackResult();
    }

    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = {IllegalArgumentException.class, BizException.class})
    public <T> Result<T> post(String url, Class<T> clazz) {
        String resp = this.post(url, JSON.toJSONString(new HashMap<>(4)));
        return this.getResult(resp, clazz);
    }

    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = {IllegalArgumentException.class, BizException.class})
    public <T> Result<T> post(String url, Map<String, Object> param, Class<T> clazz) {
        String resp = this.post(url, JSON.toJSONString(param));
        return this.getResult(resp, clazz);
    }

    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = {IllegalArgumentException.class, BizException.class})
    public <T> Result<T> post(String url, String postData, Class<T> clazz) {
        String resp = this.post(url, postData);
        return this.getResult(resp, clazz);
    }

    public <T> Result<T> fallback(String url, String postData, Class<T> clazz, Throwable throwable) {
        log.error("fallback#{}，进入回退方法，异常：", url, throwable);
        return this.fallbackResult();
    }

    @HystrixCommand(fallbackMethod = "fallbackList", ignoreExceptions = {IllegalArgumentException.class, BizException.class})
    public <T> Result<List<T>> postList(String url, Class<T> clazz) {
        String resp = this.post(url, JSON.toJSONString(new HashMap<>(4)));
        return this.getResult0(resp, clazz);
    }

    @HystrixCommand(fallbackMethod = "fallbackList", ignoreExceptions = {IllegalArgumentException.class, BizException.class})
    public <T> Result<List<T>> postList(String url, Map<String, Object> param, Class<T> clazz) {
        String resp = this.post(url, JSON.toJSONString(param));
        return this.getResult0(resp, clazz);
    }

    @HystrixCommand(fallbackMethod = "fallbackList", ignoreExceptions = {IllegalArgumentException.class, BizException.class})
    public <T> Result<List<T>> postList(String url, String postData, Class<T> clazz) {
        String resp = this.post(url, postData);
        return this.getResult0(resp, clazz);
    }

    public <T> Result<List<T>> fallbackList(String url, String postData, Class<T> clazz, Throwable throwable) {
        log.error("fallbackList#{}，进入回退方法，异常：", url, throwable);
        return this.fallbackResult();
    }


    private Result fallbackResult() {
        Result result = new Result();
        result.setCode(CommonConstant.FALLBACK_CODE);
        result.setMessage(CommonConstant.FALLBACK_MSG);
        return result;
    }

    private Result initResult(String resp) {
        Result result = new Result();
        if (Strings.isBlank(resp)) {
            result.setCode(CommonConstant.EMPTY_CODE);
            result.setMessage(CommonConstant.EMPTY_MSG);
            return result;
        }
        Integer code = JSON.parseObject(resp).getInteger(CODE);
        result.setCode(String.valueOf(code));
        return result;
    }

    private <T> Result<T> getResult(String resp, Class<T> clazz) {
        Result result = this.initResult(resp);
        if (result.getCode().equals(CommonConstant.SUCCESS)) {
            result.setData(JSON.parseObject(JSON.parseObject(resp).getString(DATA), clazz));
        } else if (result.getCode().equals(CommonConstant.EMPTY_CODE)) {
            return result;
        } else {
            result.setMessage(JSON.parseObject(resp).getString(MESSAGE));
        }
        return result;
    }

    private <T> Result<List<T>> getResult0(String resp, Class<T> clazz) {
        Result result = this.initResult(resp);
        if (result.getCode().equals(CommonConstant.SUCCESS)) {
            result.setData(JSON.parseArray(JSON.parseObject(resp).getString(DATA), clazz));
        } else if (result.getCode().equals(CommonConstant.EMPTY_CODE)) {
            return result;
        } else {
            result.setMessage(JSON.parseObject(resp).getString(MESSAGE));
        }
        return result;
    }

    private String get(String url, Map<String, Object> paramMap) {
        return HttpWrapper.create()
                .setUrl(url).addParams(paramMap).setMethod(HttpWrapper.HttpMethod.GET).executeStr();
    }

    private String post(String url, String postData) {
        return HttpWrapper.create()
                .setUrl(url).setMethod(HttpWrapper.HttpMethod.POST)
                .setPostData(postData).executeStr();
    }
}
