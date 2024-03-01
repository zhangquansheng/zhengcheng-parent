package com.zhengcheng.ums.mapper;

import com.zhengcheng.mybatis.plus.core.BaseMapperX;
import com.zhengcheng.mybatis.plus.core.LambdaQueryWrapperX;
import com.zhengcheng.ums.domain.entity.SysConfigEntity;

import cn.hutool.core.util.ObjectUtil;

public interface SysConfigMapper extends BaseMapperX<SysConfigEntity> {

    /**
     * 查询参数配置信息
     *
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    default SysConfigEntity selectConfig(SysConfigEntity config) {
        return selectOne(new LambdaQueryWrapperX<SysConfigEntity>()
                .eq(ObjectUtil.isNotEmpty(config.getConfigId()), SysConfigEntity::getConfigId, config.getConfigId())
                .eq(ObjectUtil.isNotEmpty(config.getConfigName()), SysConfigEntity::getConfigName, config.getConfigName())
                .eq(ObjectUtil.isNotEmpty(config.getConfigKey()), SysConfigEntity::getConfigKey, config.getConfigKey()));
    }

    /**
     * 校验参数键名是否唯一
     */
    default SysConfigEntity checkConfigKeyUnique(SysConfigEntity config) {
        return selectOne(new LambdaQueryWrapperX<SysConfigEntity>()
                .eq(SysConfigEntity::getConfigKey, config.getConfigKey())
        );
    }

}
