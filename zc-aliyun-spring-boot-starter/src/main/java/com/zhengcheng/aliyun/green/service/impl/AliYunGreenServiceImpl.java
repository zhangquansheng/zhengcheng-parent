package com.zhengcheng.aliyun.green.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.common.collect.Lists;
import com.zhengcheng.aliyun.green.constant.AliYunGreenConstants;
import com.zhengcheng.aliyun.green.dto.ImageSceneData;
import com.zhengcheng.aliyun.green.dto.ImageSceneResult;
import com.zhengcheng.aliyun.green.dto.TextSceneData;
import com.zhengcheng.aliyun.green.dto.TextSceneResult;
import com.zhengcheng.aliyun.green.service.IAliYunGreenService;
import com.zhengcheng.aliyun.properties.AliYunProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * AliYunGreenServiceImpl
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/2 23:34
 */
@Slf4j
@RequiredArgsConstructor
public class AliYunGreenServiceImpl implements IAliYunGreenService {

    private final AliYunProperties aliyunProperties;

    /**
     * 获取默认客户端
     *
     * @return IAcsClient
     */
    private IAcsClient getDefaultAcsClient() {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }

    @Override
    public TextSceneResult antispam(String dataId, String content) {
        TextSceneData textSceneData = new TextSceneData();
        textSceneData.setDataId(dataId);
        textSceneData.setContent(content);
        List<TextSceneResult> sceneResultList = this.batchAntispam(Lists.newArrayList(textSceneData));
        if (CollectionUtil.isNotEmpty(sceneResultList)) {
            return sceneResultList.get(0);
        }
        return new TextSceneResult();
    }

    @Override
    public List<TextSceneResult> batchAntispam(List<TextSceneData> textSceneDataList) {
        TextScanRequest textScanRequest = new TextScanRequest();
        textScanRequest.setHttpContentType(FormatType.JSON);
        List<Map<String, Object>> tasks = new ArrayList<>();
        for (TextSceneData textSceneData : textSceneDataList) {
            Map<String, Object> task = new LinkedHashMap<>();
            task.put("dataId", textSceneData.getDataId());
            //待检测的文本，长度不超过10000个字符
            task.put("content", textSceneData.getContent());
            tasks.add(task);
        }
        JSONObject data = new JSONObject();
        // 检测场景，文本垃圾检测传递：antispam
        String scene = "antispam";
        data.put("scenes", Lists.newArrayList(scene));
        data.put("tasks", tasks);
        textScanRequest.setHttpContent(data.toJSONString().getBytes(StandardCharsets.UTF_8), "UTF-8", FormatType.JSON);
        // 请务必设置超时时间
        try {
            HttpResponse httpResponse = this.getDefaultAcsClient().doAction(textScanRequest);
            if (httpResponse.isSuccess()) {
                JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), StandardCharsets.UTF_8));
                if (AliYunGreenConstants.OK == scrResponse.getInteger(AliYunGreenConstants.CODE)) {
                    return JSONArray.parseArray(scrResponse.getString("data"), TextSceneResult.class);
                }
            }
        } catch (ClientException e) {
            log.error("batchAntispam fail,textSceneData:{},message:{}", JSON.toJSONString(textSceneDataList), e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<ImageSceneResult> batchImageSyncScan(List<String> scenes, List<ImageSceneData> imageSceneDataList) {
        ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
        //支持http和https
        JSONObject httpBody = new JSONObject();
        httpBody.put("scenes", scenes);

        List<JSONObject> tasks = new ArrayList<>();
        Date time = new Date();
        for (ImageSceneData imageSceneData : imageSceneDataList) {
            JSONObject task = new JSONObject();
            task.put("dataId", imageSceneData.getDataId());
            //设置图片链接
            task.put("url", imageSceneData.getUrl());
            task.put("time", time);
            tasks.add(task);
        }
        httpBody.put("tasks", tasks);
        imageSyncScanRequest.setHttpContent(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(httpBody.toJSONString()), "UTF-8", FormatType.JSON);
        List<ImageSceneResult> sceneResultList = new ArrayList<>();
        try {
            HttpResponse httpResponse = this.getDefaultAcsClient().doAction(imageSyncScanRequest);
            //服务端接收到请求，并完成处理返回的结果
            if (httpResponse != null && httpResponse.isSuccess()) {
                JSONObject scrResponse = JSON.parseObject(org.apache.commons.codec.binary.StringUtils.newStringUtf8(httpResponse.getHttpContent()));
                if (AliYunGreenConstants.OK == scrResponse.getIntValue(AliYunGreenConstants.CODE)) {
                    return JSONArray.parseArray(scrResponse.getString("data"), ImageSceneResult.class);
                }
            }
        } catch (Exception e) {
            log.error("batchImageSyncScan fail,textSceneData:{},message:{}", JSON.toJSONString(imageSceneDataList), e.getMessage(), e);
        }
        return sceneResultList;
    }
}
