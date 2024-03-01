package com.zhengcheng.ums.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.mvc.util.PageQueryUtil;
import com.zhengcheng.mybatis.plus.core.BaseMapperX;
import com.zhengcheng.mybatis.plus.core.LambdaQueryWrapperX;
import com.zhengcheng.ums.domain.entity.SysDictTypeEntity;

import java.util.Map;

import cn.hutool.core.util.ObjectUtil;

public interface SysDictTypeMapper extends BaseMapperX<SysDictTypeEntity> {

    default PageResult<SysDictTypeEntity> selectPage(SysDictTypeEntity dictType) {
        return selectPage(PageQueryUtil.build(), new LambdaQueryWrapperX<SysDictTypeEntity>()
                .likeIfPresent(SysDictTypeEntity::getDictName, dictType.getDictName())
                .likeIfPresent(SysDictTypeEntity::getDictType, dictType.getDictType())
                .eqIfPresent(SysDictTypeEntity::getStatus, dictType.getStatus())
                .betweenIfPresent(SysDictTypeEntity::getCreateTime, dictType.getParams()));
    }


    default LambdaQueryWrapper<SysDictTypeEntity> creatWrapper(SysDictTypeEntity dictType) {
        Map<String, Object> params = dictType.getParams();
        String beginTime = (String) params.get("beginTime");
        String endTime = (String) params.get("endTime");
        return new LambdaQueryWrapperX<SysDictTypeEntity>()
                .ge(ObjectUtil.isNotEmpty(beginTime), SysDictTypeEntity::getCreateTime, beginTime)
                .le(ObjectUtil.isNotEmpty(endTime), SysDictTypeEntity::getCreateTime, endTime);

    }

}
