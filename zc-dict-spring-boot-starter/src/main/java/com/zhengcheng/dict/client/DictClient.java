package com.zhengcheng.dict.client;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.zhengcheng.common.web.Result;
import com.zhengcheng.dict.adminservice.DictAdminService;
import com.zhengcheng.dict.client.feign.DictFeignClient;
import com.zhengcheng.dict.common.DictUtils;
import com.zhengcheng.dict.dto.DictItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典客户端
 *
 * @author :    zhangquansheng
 * @date :    2020/5/25 14:28
 */
@Slf4j
@Component
public class DictClient implements MessageListener {

    @Autowired
    private DictAdminService dictAdminService;
    @Autowired
    private DictFeignClient dictFeignClient;
    @Autowired
    private Cache<String, String> caffeineCache;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        log.info("channel:[{}],receive message body: [{}]", channel, body);
        this.del(body);
    }

    /**
     * 根据类型查询数据字典列表
     *
     * @param type 类型
     * @return DictItem
     */
    public List<DictItemDTO> findByType(@NonNull String type) {
        List<DictItemDTO> dictItems = dictAdminService.findDictCacheByType(type);
        if (CollectionUtil.isNotEmpty(dictItems)) {
            return dictItems;
        }

        Result<List<DictItemDTO>> result = dictFeignClient.list(type);
        if (result.suc()) {
            List<DictItemDTO> dictItemList = result.getData();
            caffeineCache.put(DictUtils.key(type), JSONUtil.toJsonStr(dictItemList));
            return dictItemList;
        }
        return new ArrayList<>();
    }

    /**
     * 根据类型删除
     * 此方法只会在字典服务管理平台执行
     *
     * @param type 类型
     */
    public void del(String type) {
        caffeineCache.invalidate(DictUtils.key(type));
    }

}
