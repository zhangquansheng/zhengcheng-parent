package com.zhengcheng.ums.controller;

import com.zhengcheng.common.utils.PageInfo;
import com.zhengcheng.common.utils.Result;
import com.zhengcheng.common.validation.annotation.Update;
import com.zhengcheng.ums.controller.facade.DictFacade;
import com.zhengcheng.ums.dto.DictDataDTO;
import com.zhengcheng.ums.dto.DictTypeDTO;
import com.zhengcheng.ums.dto.command.DictDataCommand;
import com.zhengcheng.ums.dto.command.DictDataPageCommand;
import com.zhengcheng.ums.dto.command.DictTypeCommand;
import com.zhengcheng.ums.dto.command.DictTypePageCommand;
import com.zhengcheng.ums.dto.command.EnableCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * DictController
 *
 * @author quansheng1.zhang
 * @since 2022/4/28 18:38
 */
@Api(tags = {"字典管理"})
@RestController
@RequestMapping("/admin/dict")
public class DictController {

    @Autowired
    private DictFacade dictFacade;

    @ApiOperation("分页查询字典类型")
    @SaCheckPermission("sys:dict:main")
    @PostMapping("/type/page")
    public Result<PageInfo<DictTypeDTO>> typePage(@Valid @RequestBody DictTypePageCommand pageCommand) {
        return Result.successData(dictFacade.typePage(pageCommand));
    }

    @ApiOperation("分页查询字典")
    @SaCheckPermission("sys:dict:main")
    @PostMapping("/data/page")
    public Result<PageInfo<DictDataDTO>> dataPage(@Valid @RequestBody DictDataPageCommand dictDataPageCommand) {
        return Result.successData(dictFacade.dataPage(dictDataPageCommand));
    }

    @ApiOperation("保存字典数据")
    @SaCheckPermission("sys:dict:save")
    @PostMapping("/save/data")
    public Result<Boolean> addData(@Valid @RequestBody DictDataCommand dictDataCommand) {
        return Result.successData(dictFacade.addData(dictDataCommand));
    }

    @ApiOperation("更新字典数据")
    @SaCheckPermission("sys:dict:update")
    @PostMapping("/update/data")
    public Result<Boolean> updateData(@Validated(Update.class) @RequestBody DictDataCommand dictDataCommand) {
        return Result.successData(dictFacade.updateData(dictDataCommand));
    }

    @ApiOperation("删除字典数据")
    @SaCheckPermission("sys:dict:del")
    @DeleteMapping("/remove/data/{id}")
    public Result<Boolean> removeData(@PathVariable("id") Long id) {
        return Result.successData(dictFacade.removeData(id));
    }

    @ApiOperation("批量删除字典数据")
    @SaCheckPermission("sys:dict:del")
    @DeleteMapping("/batchRemove/data")
    public Result<Boolean> batchRemoveData(@RequestParam("ids") List<Long> ids) {
        return Result.successData(dictFacade.batchRemoveData(ids));
    }

    @ApiOperation("根据ID启用/禁用字典数据")
    @SaCheckPermission("sys:dict:update")
    @PostMapping("/enable/data")
    public Result<Boolean> enableData(@Valid @RequestBody EnableCommand enableCommand) {
        return Result.successData(dictFacade.enableData(enableCommand));
    }

    @ApiOperation("保存字典类型")
    @SaCheckPermission("sys:dict:save")
    @PostMapping("/save/type")
    public Result<Boolean> saveType(@Valid @RequestBody DictTypeCommand dictTypeCommand) {
        return Result.successData(dictFacade.saveType(dictTypeCommand));
    }

    @ApiOperation("删除字典类型")
    @SaCheckPermission("sys:dict:del")
    @DeleteMapping("/remove/type/{id}")
    public Result<Boolean> removeType(@PathVariable("id") Long id) {
        return Result.successData(dictFacade.removeType(id));
    }

    @ApiOperation("更新字典数据类型")
    @SaCheckPermission("sys:dict:update")
    @PostMapping("/update/type")
    public Result<Boolean> updateType(@Validated(Update.class) @RequestBody DictTypeCommand dictTypeCommand) {
        return Result.successData(dictFacade.updateType(dictTypeCommand));
    }

    @ApiOperation("根据ID启用/禁用字典数据类型")
    @SaCheckPermission("sys:dict:update")
    @PostMapping("/enable/type")
    public Result<Boolean> enableType(@Valid @RequestBody EnableCommand enableCommand) {
        return Result.successData(dictFacade.enableType(enableCommand));
    }

    @ApiOperation("批量删除字典数据类型")
    @SaCheckPermission("sys:dict:del")
    @DeleteMapping("/batchRemove/type")
    public Result<Boolean> batchRemoveType(@RequestParam("ids") List<Long> ids) {
        return Result.successData(dictFacade.batchRemoveType(ids));
    }
}
