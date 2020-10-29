package com.zhengcheng.core.aliyun.sms.service;

import com.zhengcheng.core.aliyun.sms.dto.SendDetailDTO;
import com.zhengcheng.core.aliyun.sms.dto.SmsDataDTO;

import java.util.Map;

/**
 * Service - 阿里云短信
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 11:24
 */
public interface IAliYunSmsService {

    /**
     * 发送短信。详情https://help.aliyun.com/document_detail/55284.html?spm=a2c4g.11186623.6.647.71163520UlAX5d
     *
     * @param phone        待发送手机号
     * @param signName     短信签名-可在短信控制台中找到
     * @param templateCode 短信模板-可在短信控制台中找到
     * @return SmsDataDTO
     */
    SmsDataDTO sendSms(String phone, String signName,
                       String templateCode);

    /**
     * 发送短信。详情https://help.aliyun.com/document_detail/55284.html?spm=a2c4g.11186623.6.647.71163520UlAX5d
     *
     * @param phone         待发送手机号
     * @param signName      短信签名-可在短信控制台中找到
     * @param templateCode  短信模板-可在短信控制台中找到
     * @param templateParam 模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时
     * @return SmsDataDTO
     */
    SmsDataDTO sendSms(String phone, String signName,
                       String templateCode, Map<String, Object> templateParam);

    /**
     * 发送短信。详情https://help.aliyun.com/document_detail/55284.html?spm=a2c4g.11186623.6.647.71163520UlAX5d
     *
     * @param phone         待发送手机号
     * @param signName      短信签名-可在短信控制台中找到
     * @param templateCode  短信模板-可在短信控制台中找到
     * @param templateParam 模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时
     * @param outId         outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
     * @return SmsDataDTO
     */
    SmsDataDTO sendSms(String phone, String signName,
                       String templateCode, Map<String, Object> templateParam, String outId);

    /**
     * 批量发送。详情https://help.aliyun.com/document_detail/66041.html
     *
     * @param phoneNumberJson 短信接收号码,JSON格式,批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
     * @param signNameJson    短信签名,JSON格式
     * @param templateCode    短信模板ID
     * @return SmsDataDTO
     */
    SmsDataDTO sendBatchSms(String phoneNumberJson, String signNameJson,
                            String templateCode);

    /**
     * 批量发送。详情https://help.aliyun.com/document_detail/66041.html
     *
     * @param phoneNumberJson   短信接收号码,JSON格式,批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
     * @param signNameJson      短信签名,JSON格式
     * @param templateCode      短信模板ID
     * @param templateParamJson 短信模板变量替换JSON串,友情提示:如果JSON中需要带换行符,请参照标准的JSON协议。
     * @return SmsDataDTO
     */
    SmsDataDTO sendBatchSms(String phoneNumberJson, String signNameJson,
                            String templateCode, String templateParamJson);

    /**
     * 批量发送。详情https://help.aliyun.com/document_detail/66041.html
     *
     * @param phoneNumberJson     短信接收号码,JSON格式,批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
     * @param signNameJson        短信签名,JSON格式
     * @param templateCode        短信模板ID
     * @param templateParamJson   短信模板变量替换JSON串,友情提示:如果JSON中需要带换行符,请参照标准的JSON协议。
     * @param smsUpExtendCodeJson 上行短信扩展码，JSON数组格式。无特殊需要此字段的用户请忽略此字段。
     * @return SmsDataDTO
     */
    SmsDataDTO sendBatchSms(String phoneNumberJson, String signNameJson,
                            String templateCode, String templateParamJson,
                            String smsUpExtendCodeJson);

    /**
     * 查询发送的短信。https://help.aliyun.com/document_detail/55289.html?spm=a2c4g.11186623.6.648.72a53144NTp88y
     *
     * @param currentPage 分页查看发送记录，指定发送记录的的当前页码。
     * @param pageSize    分页查看发送记录，指定每页显示的短信记录数量。取值范围为1~50。
     * @param phone       接收短信的手机号码。
     * @param sendDate    短信发送日期，支持查询最近30天的记录。格式为yyyyMMdd，例如20181225。
     * @return SendDetailDTO
     */
    SendDetailDTO querySendDetails(Long currentPage, Long pageSize, String phone, String sendDate);

    /**
     * 查询发送的短信。https://help.aliyun.com/document_detail/55289.html?spm=a2c4g.11186623.6.648.72a53144NTp88y
     *
     * @param currentPage 分页查看发送记录，指定发送记录的的当前页码。
     * @param pageSize    分页查看发送记录，指定每页显示的短信记录数量。取值范围为1~50。
     * @param phone       接收短信的手机号码。
     * @param sendDate    短信发送日期，支持查询最近30天的记录。格式为yyyyMMdd，例如20181225。
     * @param bizId       发送回执ID，即发送流水号。调用发送接口SendSms或SendBatchSms发送短信时，返回值中的BizId字段。
     * @return SendDetailDTO
     */
    SendDetailDTO querySendDetails(Long currentPage, Long pageSize, String phone, String sendDate, String bizId);

}
