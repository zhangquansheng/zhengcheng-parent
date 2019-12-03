package com.zhengcheng.green.service.impl;

import cn.hutool.core.collection.CollectionUtil;
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
import com.zhengcheng.green.constant.AliYunGreenConstants;
import com.zhengcheng.green.dto.SceneResult;
import com.zhengcheng.green.properties.AcsProperties;
import com.zhengcheng.green.service.IAliYunGreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
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
     * 获取默认客户端
     *
     * @return IAcsClient
     */
    public IAcsClient getDefaultAcsClient() {
        IClientProfile profile = DefaultProfile.getProfile(acsProperties.getRegionId(), acsProperties.getAccessKeyId(), acsProperties.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }


    @Override
    public SceneResult antispam(String dataId, String content) {
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
        String scene = "antispam";
        data.put("scenes", Lists.newArrayList(scene));
        data.put("tasks", tasks);
        textScanRequest.setHttpContent(data.toJSONString().getBytes(StandardCharsets.UTF_8), "UTF-8", FormatType.JSON);
        // 请务必设置超时时间
        textScanRequest.setConnectTimeout(acsProperties.getConnectTimeout());
        textScanRequest.setReadTimeout(acsProperties.getReadTimeout());
        try {
            HttpResponse httpResponse = this.getDefaultAcsClient().doAction(textScanRequest);
            if (httpResponse.isSuccess()) {
                JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), StandardCharsets.UTF_8));
                if (AliYunGreenConstants.OK == scrResponse.getInteger(AliYunGreenConstants.CODE)) {
                    JSONArray taskResults = scrResponse.getJSONArray("data");
                    for (Object taskResult : taskResults) {
                        if (AliYunGreenConstants.OK == ((JSONObject) taskResult).getInteger(AliYunGreenConstants.CODE)) {
                            List<SceneResult> sceneResultList = JSONArray.parseArray(((JSONObject) taskResult).getString("results"), SceneResult.class);
                            if (CollectionUtil.isNotEmpty(sceneResultList)) {
                                return sceneResultList.get(0);
                            }
                        }
                    }
                }
            }
        } catch (ClientException e) {
            log.error("antispam fail,dataId:{},message:{}", dataId, e.getMessage(), e);
        }
        return new SceneResult(scene, "");
    }
}
