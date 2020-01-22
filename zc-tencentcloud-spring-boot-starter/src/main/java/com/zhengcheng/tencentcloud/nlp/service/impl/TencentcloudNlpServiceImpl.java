package com.zhengcheng.tencentcloud.nlp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.nlp.v20190408.NlpClient;
import com.tencentcloudapi.nlp.v20190408.models.ChatBotRequest;
import com.tencentcloudapi.nlp.v20190408.models.ChatBotResponse;
import com.zhengcheng.tencentcloud.nlp.service.ITencentcloudNlpService;
import com.zhengcheng.tencentcloud.properties.TencentcloudProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * TencentcloudNlpServiceImpl
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 15:32
 */
@Slf4j
@RequiredArgsConstructor
public class TencentcloudNlpServiceImpl implements ITencentcloudNlpService {

    private final TencentcloudProperties tencentcloudProperties;

    /**
     * 获取默认客户端
     *
     * @return NlpClient
     */
    private NlpClient getNlpClient() {
        Credential cred = new Credential(tencentcloudProperties.getSecretId(), tencentcloudProperties.getSecretKey());

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("nlp.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        return new NlpClient(cred, tencentcloudProperties.getRegion(), clientProfile);
    }

    @Override
    public ChatBotResponse chatBot(String query, Integer flag, String openId) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("Query", query);
        if (flag != null) {
            params.put("Flag", flag);
        }
        if (StrUtil.isNotBlank(openId)) {
            params.put("OpenId", openId);
        }
        ChatBotRequest req = ChatBotRequest.fromJsonString(JSON.toJSONString(params), ChatBotRequest.class);
        try {
            return this.getNlpClient().ChatBot(req);
        } catch (TencentCloudSDKException e) {
            log.error("chatBot TencentCloudSDKException:[{}]", e.getMessage(), e);
        }
        return null;
    }
}
