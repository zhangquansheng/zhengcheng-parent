package com.zhengcheng.ums.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.ums.domain.entity.DictData;
import com.zhengcheng.ums.domain.mapper.DictDataMapper;
import com.zhengcheng.ums.service.DictDataService;

/**
 * DictDataServiceImpl
 *
 * @author quansheng1.zhang
 * @since 2022/4/29 15:01
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {
}
