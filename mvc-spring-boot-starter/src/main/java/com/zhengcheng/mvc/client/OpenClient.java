package com.zhengcheng.mvc.client;

import com.alibaba.fastjson.JSON;
import com.zhengcheng.common.enums.ErrorCodeEnum;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.http.HttpWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 调用接口的客户端
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.client
 * @Description :
 * @date :    2019/2/28 21:19
 */
@Slf4j
@Service
public class OpenClient {

    private final Integer successCode = 0;

    private final String codeKey = "code";

    private final String dataKey = "data";

    public void post(String url, String postData) {
        String resp = HttpWrapper.create()
                .setUrl(url).setMethod(HttpWrapper.HttpMethod.POST)
                .setPostData(postData).executeStr();
        getResult(url, postData, resp);
    }

    public <T> T post(String url, String postData, Class<T> clazz) {
        String resp = HttpWrapper.create()
                .setUrl(url).setMethod(HttpWrapper.HttpMethod.POST)
                .setPostData(postData).executeStr();
        return getResult(url, postData, resp, clazz);
    }

    public <T> List<T> post0(String url, String postData, Class<T> clazz) {
        String resp = HttpWrapper.create()
                .setUrl(url).setMethod(HttpWrapper.HttpMethod.POST)
                .setPostData(postData).executeStr();
        return getResult0(url, postData, resp, clazz);
    }

    public void get(String url, Map<String, Object> paramMap) {
        String resp = HttpWrapper.create()
                .setUrl(url).addParams(paramMap).setMethod(HttpWrapper.HttpMethod.GET).executeStr();
        getResult(url, JSON.toJSONString(paramMap), resp);
    }

    public <T> T get(String url, Map<String, Object> paramMap, Class<T> clazz) {
        String resp = HttpWrapper.create()
                .setUrl(url).addParams(paramMap).setMethod(HttpWrapper.HttpMethod.GET).executeStr();
        return getResult(url, JSON.toJSONString(paramMap), resp, clazz);
    }

    public <T> T get(String url, Class<T> clazz) {
        String resp = HttpWrapper.create()
                .setUrl(url).setMethod(HttpWrapper.HttpMethod.GET).executeStr();
        return getResult(url, "", resp, clazz);
    }

    public <T> List<T> get0(String url, Class<T> clazz) {
        String resp = HttpWrapper.create()
                .setUrl(url).setMethod(HttpWrapper.HttpMethod.GET).executeStr();
        return getResult0(url, "", resp, clazz);
    }

    public <T> List<T> getResult0(String url, String data, String resp, Class<T> clazz) {
        try {
            Integer code = JSON.parseObject(resp).getInteger(codeKey);
            if (code != null && code.equals(successCode)) {
                return JSON.parseArray(JSON.parseObject(resp).getString(dataKey), clazz);
            } else {
                log.info("{}#{}#{}", url, data, resp);
                throw new BizException(ErrorCodeEnum.BIZ1001);
            }
        } catch (Exception e) {
            log.error("{}#{}#{}#OpenClientException:{}", url, data, resp, e.getMessage(), e);
        }
        return null;
    }

    public <T> T getResult(String url, String data, String resp, Class<T> clazz) {
        try {
            Integer code = JSON.parseObject(resp).getInteger(codeKey);
            if (code != null && code.equals(successCode)) {
                return JSON.parseObject(JSON.parseObject(resp).getString(dataKey), clazz);
            } else {
                throw new BizException(ErrorCodeEnum.BIZ1001);
            }
        } catch (Exception e) {
            log.error("{}#{}#{}#OpenClientException:{}", url, data, resp, e.getMessage(), e);
        }
        return null;
    }

    public void getResult(String url, String data, String resp) {
        Integer code = JSON.parseObject(resp).getInteger(codeKey);
        if (code != null && !code.equals(successCode)) {
            log.info("{}#{}#{}", url, data, resp);
            throw new BizException(ErrorCodeEnum.BIZ1001);
        }
    }

}
