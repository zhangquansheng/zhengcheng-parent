package com.zhengcheng.swagger.mq.handler;


import com.alibaba.fastjson.JSONObject;
import com.zhengcheng.swagger.mq.model.Result;

/**
 * 消费者处理
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:17
 */
public interface IConsumerHandler {

    /**
     * 消费消息
     *
     * @param jsonObject 消息data
     * @return 执行结果，成功则消费消息成功，否则消费消息失败
     * @throws Exception
     */
    Result execute(JSONObject jsonObject);
}
