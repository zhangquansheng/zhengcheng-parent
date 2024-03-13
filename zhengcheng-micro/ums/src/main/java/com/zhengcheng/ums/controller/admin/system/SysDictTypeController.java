package com.zhengcheng.ums.controller.admin.system;

import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.common.domain.Result;
import com.zhengcheng.ums.domain.entity.SysDictTypeEntity;
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

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;


@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController {

    @Autowired
    private SysDictTypeService dictTypeService;

    @SaCheckPermission("system:dict:list")
    @GetMapping(value = "/list", name = "字典类型管理-分页")
    public Result list(SysDictTypeEntity sysDictTypeEntity) {
        PageResult<SysDictTypeEntity> page = dictTypeService.page(sysDictTypeEntity);
        return Result.ok().put(page);
    }

    /**
     * 查询字典类型详细
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{dictId}", name = "字典类型管理-查询")
    public Result getInfo(@PathVariable Long dictId) {
        return Result.ok(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @SaCheckPermission("system:dict:add")
    @PostMapping(name = "字典类型管理-新增")
    public Result add(@Validated @RequestBody SysDictTypeEntity dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return Result.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }

        return Result.ok(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @PutMapping(name = "字典类型管理-修改")
    public Result edit(@Validated @RequestBody SysDictTypeEntity dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return Result.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        return Result.ok(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @SaCheckPermission("system:dict:remove")
    @DeleteMapping(value = "/{dictIds}", name = "字典类型管理-删除")
    public Result remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return Result.ok();
    }

    /**
     * 刷新字典缓存
     */
    @SaCheckPermission("system:dict:remove")
    @DeleteMapping(value = "/refreshCache", name = "字典类型管理-刷新")
    public Result refreshCache() {
        dictTypeService.resetDictCache();
        return Result.ok();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping(value = "/optionselect", name = "字典类型管理-获取字典选择框列表")
    public Result optionselect() {
        List<SysDictTypeEntity> dictTypes = dictTypeService.selectDictTypeAll();
        return Result.ok(dictTypes);
    }

}
