package com.zhengcheng.ums.service;


import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.ums.domain.entity.SysDictDataEntity;

public interface SysDictDataService {

    PageResult<SysDictDataEntity> page(SysDictDataEntity dictDataEntity);

    /**
     * 新增保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    int insertDictData(SysDictDataEntity dictData);

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    SysDictDataEntity selectDictDataById(Long dictCode);

    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    int updateDictData(SysDictDataEntity dictData);

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    void deleteDictDataByIds(Long[] dictCodes);


}
