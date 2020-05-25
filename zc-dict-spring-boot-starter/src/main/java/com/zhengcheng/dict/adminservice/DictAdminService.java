package com.zhengcheng.dict.adminservice;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.zhengcheng.dict.dto.DictItemDTO;
import com.zhengcheng.dict.common.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 数据字典管理实现
 *
 * @author :    quansheng.zhang
 * @date :    2020/5/25 20:48
 */
@Component
public class DictAdminService {

    @Resource(name = "dictStringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Cache<String, String> caffeineCache;

    /**
     * 根据类型更新缓存中的数据字典
     *
     * @param type      类型
     * @param dictItems 数据字典
     */
    public void saveDictCache(@NonNull String type, List<DictItemDTO> dictItems) {
        String key = DictUtils.key(type);
        String dictItemListJsonStr = JSONUtil.toJsonStr(dictItems);
        // 一级缓存的超时时间为10分钟
        caffeineCache.put(key, dictItemListJsonStr);
        // 二级缓存的超时时间为30分钟
        stringRedisTemplate.opsForValue().set(key, dictItemListJsonStr, 30, TimeUnit.MINUTES);
    }

    /**
     * 根据类型，从缓存中获取字典
     *
     * @param type 类型
     * @return DictItemDTO
     */
    public List<DictItemDTO> findDictCacheByType(@NonNull String type) {
        String key = DictUtils.key(type);
        String dictListJson = caffeineCache.getIfPresent(key);
        if (StrUtil.isNotEmpty(dictListJson)) {
            return JSONUtil.toList(JSONUtil.parseArray(dictListJson), DictItemDTO.class);
        }

        dictListJson = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isNotEmpty(dictListJson)) {
            caffeineCache.put(key, dictListJson);
            return JSONUtil.toList(JSONUtil.parseArray(dictListJson), DictItemDTO.class);
        }
        return new ArrayList<>();
    }

    /**
     * 根据类型，同步所有缓存，并发送消息给客户端
     *
     * @param type 字典类型
     */
    public void delAndSend(String type) {
        // 第一步、删除redis 分布式缓存
        stringRedisTemplate.delete(DictUtils.key(type));

        // 第二步、删除内存的缓存
        caffeineCache.invalidate(DictUtils.key(type));

        //　发送消息，通知客户端更新
        stringRedisTemplate.convertAndSend(DictUtils.PATTERN_TOPIC, type);
    }

}
