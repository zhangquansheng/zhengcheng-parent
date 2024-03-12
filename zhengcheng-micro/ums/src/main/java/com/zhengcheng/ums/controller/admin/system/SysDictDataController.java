package com.zhengcheng.ums.controller.admin.system;

import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.common.domain.Result;
import com.zhengcheng.ums.domain.entity.SysDictDataEntity;
import com.zhengcheng.ums.service.SysDictDataService;
import com.zhengcheng.ums.service.SysDictTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollUtil;

@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController {
    @Autowired
    private SysDictDataService dictDataService;
    @Autowired
    private SysDictTypeService dictTypeService;


    @GetMapping(value = "/list", name = "字典数据管理-分页")
    public Result page(SysDictDataEntity dictData) {
        PageResult<SysDictDataEntity> page = dictDataService.page(dictData);
        return Result.ok().put(page);
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}", name = "字典数据管理-根据字典类型查询字典数据信息")
    public Result dictType(@PathVariable String dictType) {
        List<SysDictDataEntity> data = dictTypeService.selectDictDataByType(dictType);
        if (CollUtil.isEmpty(data)) {
            data = new ArrayList<>();
        }
        return Result.ok().put(data);
    }


    /**
     * 查询字典数据详细
     */
    @GetMapping(value = "/{dictCode}", name = "字典数据管理-查询")
    public Result getInfo(@PathVariable Long dictCode) {
        return Result.ok(dictDataService.selectDictDataById(dictCode));
    }


    /**
     * 新增字典类型
     */
    @PostMapping(name = "字典数据管理-新增")
    public Result add(@Validated @RequestBody SysDictDataEntity dict) {
        return Result.ok(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PutMapping(name = "字典数据管理-修改")
    public Result edit(@Validated @RequestBody SysDictDataEntity dict) {
        return Result.ok(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping(value = "/{dictCodes}", name = "字典数据管理-删除")
    public Result remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return Result.ok();
    }
}
