package com.zhengcheng.ums.service.impl;


import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.ums.common.utils.DictUtils;
import com.zhengcheng.ums.domain.entity.SysDictDataEntity;
import com.zhengcheng.ums.mapper.SysDictDataMapper;
import com.zhengcheng.ums.service.SysDictDataService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class SysDictDataServiceImpl implements SysDictDataService {

    @Resource
    private SysDictDataMapper dictDataMapper;


    @Override
    public PageResult<SysDictDataEntity> page(SysDictDataEntity dictDataEntity) {
        return dictDataMapper.selectPage(dictDataEntity);
    }

    @Override
    public int insertDictData(SysDictDataEntity dictData) {
        int row = dictDataMapper.insert(dictData);
        if (row > 0) {
            List<SysDictDataEntity> dictDatas = dictDataMapper.selectDictDataByType(dictData.getDictType());
            DictUtils.setDictCache(dictData.getDictType(), dictDatas);
        }
        return row;
    }

    @Override
    public SysDictDataEntity selectDictDataById(Long dictCode) {
        return dictDataMapper.selectById(dictCode);
    }


    @Override
    public int updateDictData(SysDictDataEntity data) {
        int row = dictDataMapper.updateById(data);
        if (row > 0) {
            List<SysDictDataEntity> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    @Override
    public void deleteDictDataByIds(Long[] dictCodes) {
        for (Long dictCode : dictCodes) {
            SysDictDataEntity data = dictDataMapper.selectById(dictCode);
            dictDataMapper.deleteById(dictCode);
            List<SysDictDataEntity> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
    }
}
