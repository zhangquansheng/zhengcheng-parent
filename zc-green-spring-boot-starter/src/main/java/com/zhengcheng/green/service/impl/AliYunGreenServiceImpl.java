package com.zhengcheng.green.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.common.collect.Lists;
import com.zhengcheng.green.dto.SceneResult;
import com.zhengcheng.green.properties.AcsProperties;
import com.zhengcheng.green.service.IAliYunGreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AliYunGreenServiceImpl
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/2 23:34
 */
@Slf4j
@RequiredArgsConstructor
public class AliYunGreenServiceImpl implements IAliYunGreenService {

    private final AcsProperties acsProperties;

    /**
     * 成功返回码
     */
    private final int okCode = 200;

    /**
     * 获取默认客户端
     *
     * @return IAcsClient
     */
    public IAcsClient getDefaultAcsClient() {
        IClientProfile profile = DefaultProfile.getProfile(acsProperties.getRegionId(), acsProperties.getAccessKeyId(), acsProperties.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }


    @Override
    public List<SceneResult> antispam(String dataId, String content) throws UnsupportedEncodingException, ClientException {
        TextScanRequest textScanRequest = new TextScanRequest();
        textScanRequest.setAcceptFormat(FormatType.JSON);
        textScanRequest.setHttpContentType(FormatType.JSON);
        textScanRequest.setMethod(com.aliyuncs.http.MethodType.POST);
        textScanRequest.setEncoding("UTF-8");
        textScanRequest.setRegionId(acsProperties.getRegionId());
        List<Map<String, Object>> tasks = new ArrayList<>();
        Map<String, Object> task1 = new LinkedHashMap<>();
        task1.put("dataId", dataId);
        //待检测的文本，长度不超过10000个字符
        task1.put("content", content);
        tasks.add(task1);
        JSONObject data = new JSONObject();
        // 检测场景，文本垃圾检测传递：antispam
        data.put("scenes", Lists.newArrayList("antispam"));
        data.put("tasks", tasks);
        textScanRequest.setHttpContent(data.toJSONString().getBytes("UTF-8"), "UTF-8", FormatType.JSON);
        // 请务必设置超时时间
        textScanRequest.setConnectTimeout(acsProperties.getConnectTimeout());
        textScanRequest.setReadTimeout(acsProperties.getReadTimeout());
        HttpResponse httpResponse = this.getDefaultAcsClient().doAction(textScanRequest);
        if (httpResponse.isSuccess()) {
            JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), "UTF-8"));
            System.out.println(JSON.toJSONString(scrResponse, true));
            if (okCode == scrResponse.getInteger("code")) {
                JSONArray taskResults = scrResponse.getJSONArray("data");
                for (Object taskResult : taskResults) {
                    if (okCode == ((JSONObject) taskResult).getInteger("code")) {
                        return JSONArray.parseArray(((JSONObject) taskResult).getString("results"), SceneResult.class);
                    }
                }
            }
        }
        return null;
    }
}
