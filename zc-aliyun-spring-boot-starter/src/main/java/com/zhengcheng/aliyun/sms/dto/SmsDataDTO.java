package com.zhengcheng.aliyun.sms.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 阿里云发送消息返回数据
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 13:28
 */
@Data
public class SmsDataDTO implements Serializable {

    private static final long serialVersionUID = 8700402947703805860L;
    /**
     * 状态码的描述。
     */
    private String message;
    /**
     * 请求ID。
     */
    private String requestId;
    /**
     * 发送回执ID，可根据该ID在接口QuerySendDetails中查询具体的发送状态。
     */
    private String bizId;
    /**
     * 请求状态码。
     * <p>
     * 返回OK代表请求成功。
     */
    private String code;

    /**
     * 短信是否发送成功
     *
     * @return 是否发送成功
     */
    public boolean isSuccess() {
        String successCode = "OK";
        return successCode.equals(this.code);
    }
}
