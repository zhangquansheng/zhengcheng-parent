package com.zhengcheng.dict.client.feign;

import com.zhengcheng.common.web.Result;
import com.zhengcheng.dict.client.feign.fallback.DictFeignClientFallbackFactory;
import com.zhengcheng.dict.dto.DictItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * DictFeignClient
 *
 * @author :    quansheng.zhang
 * @date :    2020/5/6 20:47
 */
@FeignClient(name = "zc-dict", url = "${zc.dict.url}", fallbackFactory = DictFeignClientFallbackFactory.class)
public interface DictFeignClient {

    /**
     * 根据字典类型查询数据字典
     *
     * @param type 字典类型
     * @return Result
     */
    @GetMapping("")
    Result<List<DictItemDTO>> list(@RequestParam("type") String type);

}
