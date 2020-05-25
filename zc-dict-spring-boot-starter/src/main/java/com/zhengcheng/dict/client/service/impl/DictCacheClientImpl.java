package com.zhengcheng.dict.client.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.zhengcheng.common.web.Result;
import com.zhengcheng.dict.client.domain.DictItemDTO;
import com.zhengcheng.dict.client.service.DictCacheClient;
import com.zhengcheng.dict.client.service.feign.DictFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * DictClientImpl
 *
 * @author :    quansheng.zhang
 * @date :    2020/5/25 19:47
 */
@Service
public class DictCacheClientImpl implements DictCacheClient {

    @Resource(name = "dictStringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Cache<String, String> caffeineCache;
    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public List<DictItemDTO> findByType(String type) {
        String dictListJson = caffeineCache.getIfPresent(this.key(type));
        if (StrUtil.isNotEmpty(dictListJson)) {
            return JSONUtil.toList(JSONUtil.parseArray(dictListJson), DictItemDTO.class);
        }

        dictListJson = stringRedisTemplate.opsForValue().get(this.key(type));
        if (StrUtil.isNotEmpty(dictListJson)) {
            caffeineCache.put(this.key(type), dictListJson);
            return JSONUtil.toList(JSONUtil.parseArray(dictListJson), DictItemDTO.class);
        }

        Result<List<DictItemDTO>> result = dictFeignClient.list(type);
        if (result.suc()) {
            List<DictItemDTO> dictItemList = result.getData();
            caffeineCache.put(this.key(type), JSONUtil.toJsonStr(dictItemList));
            return dictItemList;
        }
        return new ArrayList<>();
    }

    @Override
    public void del(String type) {
        caffeineCache.invalidate(this.key(type));
    }

    private String key(String type) {
        return StrUtil.format("zc:d:t:{}", type);
    }
}
