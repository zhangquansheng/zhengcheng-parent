package com.zhengcheng.ums.service.impl;


import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.mybatis.plus.core.LambdaQueryWrapperX;
import com.zhengcheng.ums.common.utils.DictUtils;
import com.zhengcheng.ums.domain.entity.SysDictDataEntity;
import com.zhengcheng.ums.domain.entity.SysDictTypeEntity;
import com.zhengcheng.ums.mapper.SysDictDataMapper;
import com.zhengcheng.ums.mapper.SysDictTypeMapper;
import com.zhengcheng.ums.service.SysDictTypeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;

import static com.zhengcheng.ums.common.utils.DictUtils.clearDictCache;


@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {
    @Resource
    private SysDictTypeMapper dictTypeMapper;
    @Resource
    private SysDictDataMapper dictDataMapper;

    @Override
    public PageResult<SysDictTypeEntity> page(SysDictTypeEntity sysDictTypeEntity) {
        return dictTypeMapper.selectPage(sysDictTypeEntity);
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictDataEntity> selectDictDataByType(String dictType) {
        List<SysDictDataEntity> dictDatas = DictUtils.getDictCache(dictType);
        if (CollUtil.isNotEmpty(dictDatas)) {
            return dictDatas;
        }

        dictDatas = dictDataMapper.selectDictDataByType(dictType);

        if (CollUtil.isNotEmpty(dictDatas)) {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;


    }

    @Override
    public SysDictTypeEntity selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectById(dictId);
    }

    @Override
    public List<SysDictTypeEntity> selectDictTypeAll() {
        return dictTypeMapper.selectList();
    }

    @Override
    @Transactional
    public int updateDictType(SysDictTypeEntity dictType) {
        SysDictTypeEntity oldDict = dictTypeMapper.selectById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        //把要更新的dictType的内容赋值到oldDict
//        BeanUtil.copyProperties(dictType, oldDict);
        int row = dictTypeMapper.updateById(dictType);

        if (row > 0) {
            List<SysDictDataEntity> dictDatas = dictDataMapper.selectDictDataByType(dictType.getDictType());
            DictUtils.setDictCache(dictType.getDictType(), dictDatas);
        }
        return row;
    }

    @Override
    public int insertDictType(SysDictTypeEntity dictType) {
        int row = dictTypeMapper.insert(dictType);
        if (row > 0) {
            DictUtils.setDictCache(dictType.getDictType(), null);
        }
        return row;
    }

    @Override
    public void deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictTypeEntity dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new BizException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
//            this.removeById(dictId);
            dictTypeMapper.deleteById(dictId);
            DictUtils.removeDictCache(dictType.getDictType());
        }
    }

    @Override
    public void resetDictCache() {
        clearDictCache();
        loadingDictCache();
    }

    /**
     * 加载字典缓存数据
     */
    @Override
    public void loadingDictCache() {
        Map<String, List<SysDictDataEntity>> dictDataMap = dictDataMapper.selectList("status", "0").stream().collect(Collectors.groupingBy(SysDictDataEntity::getDictType));
        for (Map.Entry<String, List<SysDictDataEntity>> entry : dictDataMap.entrySet()) {
            DictUtils.setDictCache(entry.getKey(), entry.getValue().stream().sorted(Comparator.comparing(SysDictDataEntity::getDictSort)).collect(Collectors.toList()));
        }
    }

    @Override
    public boolean checkDictTypeUnique(SysDictTypeEntity dictType) {
        Long dictId = ObjectUtil.isNull(dictType.getDictId()) ? -1L : dictType.getDictId();
        SysDictTypeEntity info = dictTypeMapper.selectOne(new LambdaQueryWrapperX<SysDictTypeEntity>()
                .eq(SysDictTypeEntity::getDictType, dictType.getDictType()));
        if (ObjectUtil.isNotNull(info) && info.getDictId().longValue() != dictId.longValue()) {
            return false;
        }
        return true;
    }

}
