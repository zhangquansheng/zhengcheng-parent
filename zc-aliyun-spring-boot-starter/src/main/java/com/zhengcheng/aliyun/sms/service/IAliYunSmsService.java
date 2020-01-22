package com.zhengcheng.aliyun.sms.service;

import com.zhengcheng.aliyun.sms.dto.SendDetailDTO;
import com.zhengcheng.aliyun.sms.dto.SmsDataDTO;

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
