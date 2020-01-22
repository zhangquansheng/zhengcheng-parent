package com.zhengcheng.aliyun.constant;

/**
 * 全局公共常量
 *
 * @author :    quansheng.zhang
 * @date :   2019/12/2 23:34
 */
public interface AliYunGreenConstants {
    /**
     * 成功
     */
    int OK = 200;
    /**
     * 返回码
     */
    String CODE = "code";
    /**
     * 超时时间, 服务端全链路处理超时时间为10秒
     */
    Integer connectTimeout = 3000;
    /**
     * ReadTimeout小于服务端处理的时间，程序中会获得一个read timeout异常
     */
    Integer readTimeout = 10000;
}
