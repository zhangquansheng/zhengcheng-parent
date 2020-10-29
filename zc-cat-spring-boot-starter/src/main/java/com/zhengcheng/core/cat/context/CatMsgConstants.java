package com.zhengcheng.core.cat.context;

/**
 * 常量
 *
 * @author :    zhangquansheng
 * @date :    2019/12/10 11:22
 */
public class CatMsgConstants {
    /**
     * Type 常量
     */
    public static final String TYPE_URL_METHOD = "URL.method";
    public static final String TYPE_URL_CLIENT = "URL.client";


    public static final String PROVIDER_CALL_SERVER = "RemoteCall.client";

    /**
     * 客户端调用标识
     */
    public static final String PROVIDER_CALL_APP = "RemoteCall.app";

    /**
     * http header 常量
     */
    public static final String CAT_HTTP_HEADER_ROOT_MESSAGE_ID = "X-CAT-ROOT-MESSAGE-ID";
    public static final String CAT_HTTP_HEADER_PARENT_MESSAGE_ID = "X-CAT-ROOT-PARENT-ID";
    public static final String CAT_HTTP_HEADER_CHILD_MESSAGE_ID = "X-CAT-ROOT-CHILD-ID";


    /**
     * 客户端调用的服务名称 -> 最好是Cat.getManager().getDomain()获取
     */
    public static final String APPLICATION_KEY = "application.name";
}
