package com.zhengcheng.dict.client.service;

import com.zhengcheng.dict.client.domain.DictItem;

import java.util.List;

/**
 * 字典客户端
 *
 * @author :    zhangquansheng
 * @date :    2020/5/25 14:28
 */
public interface DictClient {

    /**
     * 根据类型查询数据字典列表
     *
     * @param type 类型
     * @return DictItem
     */
    List<DictItem> findByType(String type);
}
