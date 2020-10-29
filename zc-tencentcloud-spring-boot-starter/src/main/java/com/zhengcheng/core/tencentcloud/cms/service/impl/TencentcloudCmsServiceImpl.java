package com.zhengcheng.core.tencentcloud.cms.service.impl;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.tencentcloudapi.cms.v20190321.CmsClient;
import com.tencentcloudapi.cms.v20190321.models.ImageModerationRequest;
import com.tencentcloudapi.cms.v20190321.models.ImageModerationResponse;
import com.tencentcloudapi.cms.v20190321.models.TextModerationRequest;
import com.tencentcloudapi.cms.v20190321.models.TextModerationResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.zhengcheng.core.tencentcloud.cms.service.ITencentcloudCmsService;
import com.zhengcheng.core.tencentcloud.properties.TencentcloudProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * TencentcloudCmsServiceImpl
 *
 * @author :    zhangquansheng
 * @date :    2020/1/16 17:21
 */
@Slf4j
@RequiredArgsConstructor
public class TencentcloudCmsServiceImpl implements ITencentcloudCmsService {

    private final TencentcloudProperties tencentcloudProperties;

    /**
     * 获取默认客户端
     *
     * @return IAcsClient
     */
    private CmsClient getCmsClient() {
        Credential cred = new Credential(tencentcloudProperties.getSecretId(), tencentcloudProperties.getSecretKey());

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("cms.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        return new CmsClient(cred, tencentcloudProperties.getRegion(), clientProfile);
    }

    @Override
    public TextModerationResponse text(String content) {
        Map<String, String> params = new HashMap<>(16);
        params.put("Content", Base64.encode(content));
        TextModerationRequest textReq = TextModerationRequest.fromJsonString(JSON.toJSONString(params), TextModerationRequest.class);
        try {
            // https://cloud.tencent.com/document/api/669/34506#TextData
            return this.getCmsClient().TextModeration(textReq);
        } catch (TencentCloudSDKException e) {
            log.error("{}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public ImageModerationResponse imageFileContent(String fileContent) {
        Map<String, String> params = new HashMap<>(16);
        params.put("FileContent", fileContent);
        ImageModerationRequest imageReq = ImageModerationRequest.fromJsonString(JSON.toJSONString(params), ImageModerationRequest.class);
        try {
            // https://cloud.tencent.com/document/api/669/34506#ImageData
            return this.getCmsClient().ImageModeration(imageReq);
        } catch (TencentCloudSDKException e) {
            log.error("{}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public ImageModerationResponse imageFileMD5(String fileMD5) {
        Map<String, String> params = new HashMap<>(16);
        params.put("FileMD5", fileMD5);
        ImageModerationRequest imageReq = ImageModerationRequest.fromJsonString(JSON.toJSONString(params), ImageModerationRequest.class);
        try {
            // https://cloud.tencent.com/document/api/669/34506#ImageData
            return this.getCmsClient().ImageModeration(imageReq);
        } catch (TencentCloudSDKException e) {
            log.error("{}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public ImageModerationResponse imageFileUrl(String fileUrl) {
        Map<String, String> params = new HashMap<>(16);
        params.put("FileUrl", fileUrl);
        ImageModerationRequest imageReq = ImageModerationRequest.fromJsonString(JSON.toJSONString(params), ImageModerationRequest.class);
        try {
            // https://cloud.tencent.com/document/api/669/34506#ImageData
            return this.getCmsClient().ImageModeration(imageReq);
        } catch (TencentCloudSDKException e) {
            log.error("{}", e.getMessage(), e);
        }
        return null;
    }
}
