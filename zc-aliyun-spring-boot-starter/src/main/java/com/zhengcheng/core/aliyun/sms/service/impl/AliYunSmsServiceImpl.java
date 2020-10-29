package com.zhengcheng.core.aliyun.sms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zhengcheng.core.aliyun.properties.AliYunProperties;
import com.zhengcheng.core.aliyun.sms.dto.SendDetailDTO;
import com.zhengcheng.core.aliyun.sms.dto.SmsDataDTO;
import com.zhengcheng.core.aliyun.sms.properties.SmsProperties;
import com.zhengcheng.core.aliyun.sms.service.IAliYunSmsService;
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

    private final String sysDomain = "dysmsapi.aliyuncs.com";

    private final String sysVersion = "2017-05-25";

    private final AliYunProperties aliyunProperties;

    private final SmsProperties smsProperties;

    /**
     * 获取默认客户端
     *
     * @return IAcsClient
     */
    private IAcsClient getDefaultAcsClient() {
        IClientProfile profile = DefaultProfile.getProfile(StrUtil.isBlank(smsProperties.getRegionId()) ? "cn-hangzhou" : smsProperties.getRegionId(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }

    @Override
    public SmsDataDTO sendSms(String phone, String signName, String templateCode) {
        return this.sendSms(phone, signName, templateCode, null, null);
    }

    @Override
    public SmsDataDTO sendSms(String phone, String signName, String templateCode, Map<String, Object> templateParam) {
        return this.sendSms(phone, signName, templateCode, templateParam, null);
    }

    @Override
    public SmsDataDTO sendSms(String phone, String signName, String templateCode, Map<String, Object> templateParam, String outId) {
        CommonRequest request = new CommonRequest();
        request.setSysDomain(sysDomain);
        request.setSysVersion(sysVersion);
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
                return JSON.parseObject(commonResponse.getData(), SmsDataDTO.class);
            }
        } catch (ClientException e) {
            log.error("sendSms ClientException: [{}]", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public SmsDataDTO sendBatchSms(String phoneNumberJson, String signNameJson, String templateCode) {
        return this.sendBatchSms(phoneNumberJson, signNameJson, templateCode, null, null);
    }

    @Override
    public SmsDataDTO sendBatchSms(String phoneNumberJson, String signNameJson, String templateCode, String templateParamJson) {
        return this.sendBatchSms(phoneNumberJson, signNameJson, templateCode, templateParamJson, null);
    }

    @Override
    public SmsDataDTO sendBatchSms(String phoneNumberJson, String signNameJson, String templateCode, String templateParamJson, String smsUpExtendCodeJson) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(sysDomain);
        request.setSysVersion(sysVersion);
        request.setSysAction("SendBatchSms");
        request.putQueryParameter("PhoneNumberJson", phoneNumberJson);
        request.putQueryParameter("SignNameJson", signNameJson);
        request.putQueryParameter("TemplateCode", templateCode);
        if (StrUtil.isNotBlank(templateParamJson)) {
            request.putQueryParameter("TemplateParamJson", templateParamJson);
        }
        if (StrUtil.isNotBlank(smsUpExtendCodeJson)) {
            request.putQueryParameter("SmsUpExtendCodeJson", smsUpExtendCodeJson);
        }
        try {
            CommonResponse response = this.getDefaultAcsClient().getCommonResponse(request);
            if (response != null) {
                return JSON.parseObject(response.getData(), SmsDataDTO.class);
            }
        } catch (ServerException e) {
            log.error("sendBatchSms ServerException: [{}]", e.getMessage(), e);
        } catch (ClientException e) {
            log.error("sendBatchSms ClientException: [{}]", e.getMessage(), e);
        }
        return new SmsDataDTO();
    }

    @Override
    public SendDetailDTO querySendDetails(Long currentPage, Long pageSize, String phone, String sendDate) {
        return this.querySendDetails(currentPage, pageSize, phone, sendDate, null);
    }

    @Override
    public SendDetailDTO querySendDetails(Long currentPage, Long pageSize, String phone, String sendDate, String bizId) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(sysDomain);
        request.setSysVersion(sysVersion);
        request.setSysAction("QuerySendDetails");
        request.putQueryParameter("PhoneNumber", phone);
        request.putQueryParameter("SendDate", sendDate);
        request.putQueryParameter("PageSize", String.valueOf(pageSize));
        request.putQueryParameter("CurrentPage", String.valueOf(currentPage));
        if (StrUtil.isNotBlank(bizId)) {
            request.putQueryParameter("BizId", bizId);
        }
        try {
            CommonResponse response = this.getDefaultAcsClient().getCommonResponse(request);
            if (response != null) {
                return JSON.parseObject(response.getData(), SendDetailDTO.class);
            }
        } catch (ServerException e) {
            log.error("querySendDetails ServerException: [{}]", e.getMessage(), e);
        } catch (ClientException e) {
            log.error("querySendDetails ClientException: [{}]", e.getMessage(), e);
        }
        return new SendDetailDTO();
    }
}
