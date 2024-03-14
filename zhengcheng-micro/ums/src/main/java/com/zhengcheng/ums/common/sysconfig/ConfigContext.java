package com.zhengcheng.ums.common.sysconfig;


import com.zhengcheng.ums.service.SysConfigService;

import cn.hutool.extra.spring.SpringUtil;

/**
 * 系统配置
 */
public class ConfigContext {

    /**
     * 获取系统配置操作接口
     */
    public static SysConfigService me() {
        return SpringUtil.getBean(SysConfigService.class);
    }

}
