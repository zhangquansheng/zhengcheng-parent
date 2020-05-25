package com.zhengcheng.dict.client.service;

import com.zhengcheng.dict.client.domain.DictItemDTO;

import java.util.List;

/**
 * 字典客户端
 *
 * @author :    zhangquansheng
 * @date :    2020/5/25 14:28
 */
public interface DictCacheClient {

    /**
     * 根据类型查询数据字典列表
     *
     * @param type 类型
     * @return DictItem
     */
    List<DictItemDTO> findByType(String type);

    /**
     * 根据类型删除
     *  此方法只会在字典服务管理平台执行
     * @param type 类型
     */
    void del(String type);
}
