package com.zhengcheng.aliyun.sms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zhengcheng.aliyun.properties.AliYunProperties;
import com.zhengcheng.aliyun.sms.dto.SmsDataDTO;
import com.zhengcheng.aliyun.sms.service.IAliYunSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * AliyunSmsServiceImpl
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 11:28
 */
@Slf4j
@RequiredArgsConstructor
public class AliYunSmsServiceImpl implements IAliYunSmsService {

    private final AliYunProperties aliyunProperties;

    /**
     * 获取默认客户端
     *
     * @return IAcsClient
     */
    private IAcsClient getDefaultAcsClient() {
        IClientProfile profile = DefaultProfile.getProfile(aliyunProperties.getRegionId(), aliyunProperties.getAccessKey(), aliyunProperties.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }

    @Override
    public SmsDataDTO sendSms(String phone, String signName, String templateCode, Map<String, Object> templateParam, String outId) {
        CommonRequest request = new CommonRequest();
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        // 接收短信的手机号码
        request.putQueryParameter("PhoneNumbers", phone);
        // 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
        request.putQueryParameter("SignName", signName);
        // 短信模板ID
        request.putQueryParameter("TemplateCode", templateCode);
        // 短信模板变量对应的实际值，JSON格式。
        if (CollectionUtil.isNotEmpty(templateParam)) {
            request.putQueryParameter("TemplateParam", JSON.toJSONString(templateParam));
        }
        if (StrUtil.isNotBlank(outId)) {
            request.putQueryParameter("OutId", outId);
        }
        try {
            CommonResponse commonResponse = this.getDefaultAcsClient().getCommonResponse(request);
            if (commonResponse != null) {
                String data = commonResponse.getData();
                return JSON.parseObject(data, SmsDataDTO.class);
            }
        } catch (ClientException e) {
            log.error("sendSms ClientException: [{}]", e.getMessage(), e);
        }
        return null;
    }
}
