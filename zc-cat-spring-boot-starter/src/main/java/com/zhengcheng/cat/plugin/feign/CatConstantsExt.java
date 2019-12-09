package com.zhengcheng.cat.plugin.feign;

import com.dianping.cat.CatConstants;

/**
 * 1、继承、扩展CatConstants常量类，添加一些常用的Type
 * 2、添加header常量，用于http协议传输rootId、parentId、childId三个context属性
 *
 * @author soar
 * @date 2019-01-10
 */
public class CatConstantsExt extends CatConstants {
    /**
     * http header 常量
     */
    public static final String CAT_HTTP_HEADER_ROOT_MESSAGE_ID = "X-CAT-ROOT-MESSAGE-ID";
    public static final String CAT_HTTP_HEADER_PARENT_MESSAGE_ID = "X-CAT-ROOT-PARENT-ID";
    public static final String CAT_HTTP_HEADER_CHILD_MESSAGE_ID = "X-CAT-ROOT-CHILD-ID";
}
