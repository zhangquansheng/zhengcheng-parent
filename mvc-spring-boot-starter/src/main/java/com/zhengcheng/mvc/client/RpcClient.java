package com.zhengcheng.mvc.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    private final Integer SUCCESS = 0;

    private final String CODE = "code";

    private final String DATA = "data";

    private final String MESSAGE = "message";


    public String getFallback(String url, Map<String, Object> paramMap) {
        log.error("getFallback#{}#{}", url, paramMap);
        return JSONObject.toJSONString(Result.errorMessage("接口超时"));
    }

    @HystrixCommand(fallbackMethod = "getFallback")
    private String get(String url, Map<String, Object> paramMap) {
        return HttpWrapper.create()
                .setUrl(url).addParams(paramMap).setMethod(HttpWrapper.HttpMethod.GET).executeStr();
    }

    public String postFallback(String url, String postData) {
        log.error("postFallback#{}#{}", url, postData);
        return JSONObject.toJSONString(Result.errorMessage("接口超时"));
    }

    @HystrixCommand(fallbackMethod = "postFallback")
    private String post(String url, String postData) {
        return HttpWrapper.create()
                .setUrl(url).setMethod(HttpWrapper.HttpMethod.POST)
                .setPostData(postData).executeStr();
    }

    public void post(String url, Map<String, Object> paramMap) {
        this.post(url, JSON.toJSONString(paramMap));
    }

    public Integer postCode(String url, Map<String, Object> paramMap) {
        String resp = this.post(url, JSON.toJSONString(paramMap));
        JSONObject result = JSON.parseObject(resp);
        Integer code = result.getInteger("code");
        return code;
    }

    public <T> T post(String url, Map<String, Object> paramMap, Class<T> clazz) {
        String paramData = JSON.toJSONString(paramMap);
        String resp = this.post(url, paramData);
        return getResult(url, paramData, resp, clazz);
    }

    public <T> List<T> post0(String url, Map<String, Object> paramMap, Class<T> clazz) {
        String paramData = JSON.toJSONString(paramMap);
        String resp = this.post(url, paramData);
        return getResult0(url, paramData, resp, clazz);
    }

    public <T> T get(String url, Map<String, Object> paramMap, Class<T> clazz) {
        String resp = this.get(url, paramMap);
        return getResult(url, "", resp, clazz);
    }

    public String getData(String url, Map<String, Object> paramMap) {
        String resp = this.get(url, paramMap);
        if (Strings.isBlank(resp)) {
            return null;
        }
        return JSON.parseObject(resp).getString(DATA);
    }

    public <T> T get(String url, Class<T> clazz) {
        String resp = this.get(url, new HashMap<>(4));
        return getResult(url, "", resp, clazz);
    }

    public <T> List<T> get0(String url, Class<T> clazz) {
        String resp = this.get(url, new HashMap<>(4));
        return getResult0(url, "", resp, clazz);
    }

    public <T> List<T> getResult0(String url, String paramData, String resp, Class<T> clazz) {
        try {
            if (Strings.isBlank(resp)) {
                return null;
            }
            Integer code = JSON.parseObject(resp).getInteger(CODE);
            if (code != null && code.equals(SUCCESS)) {
                return JSON.parseArray(JSON.parseObject(resp).getString(DATA), clazz);
            } else {
                throw new BizException("", JSON.parseObject(resp).getString(MESSAGE));
            }
        } catch (Exception e) {
            log.info("RpcClientException#{}#{}#{}", url, paramData, resp, e.getMessage());
        }
        return null;
    }

    public <T> T getResult(String url, String paramData, String resp, Class<T> clazz) {
        try {
            if (Strings.isBlank(resp)) {
                return null;
            }
            Integer code = JSON.parseObject(resp).getInteger(CODE);
            if (code != null && code.equals(SUCCESS)) {
                return JSON.parseObject(JSON.parseObject(resp).getString(DATA), clazz);
            } else {
                throw new BizException("", JSON.parseObject(resp).getString(MESSAGE));
            }
        } catch (Exception e) {
            log.info("RpcClientException#{}#{}#{}", url, paramData, resp, e.getMessage());
        }
        return null;
    }


}
