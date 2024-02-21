package com.zhengcheng.ums.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.ums.domain.entity.Area;
import com.zhengcheng.ums.domain.mapper.AreaMapper;
import com.zhengcheng.ums.service.AreaService;

/**
 * AreaServiceImpl
 *
 * @author quansheng1.zhang
 * @since 2022/5/10 11:00
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {
}
