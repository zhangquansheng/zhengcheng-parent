package com.zhengcheng.tencentcloud.nlp.service;

import com.tencentcloudapi.nlp.v20190408.models.ChatBotResponse;

/**
 * Service - 自然语言处理-NLP
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 15:29
 */
public interface ITencentcloudNlpService {

    /**
     * 闲聊机器人
     *
     * @param query  用户请求的query
     * @param flag   0: 通用闲聊, 1:儿童闲聊, 默认是通用闲聊
     * @param openId 服务的id, 主要用于儿童闲聊接口，比如手Q的openid
     * @return ChatBotResponse
     */
    ChatBotResponse chatBot(String query, Integer flag, String openId);
}
