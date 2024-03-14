package com.zhengcheng.ums.controller.admin.system;

import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.common.domain.Result;
import com.zhengcheng.ums.domain.entity.SysConfigEntity;
import com.zhengcheng.ums.service.SysConfigService;

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

import cn.dev33.satoken.annotation.SaCheckPermission;

/**
 * 配置管理
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController {
    @Autowired
    private SysConfigService configService;

    @SaCheckPermission("system:config:list")
    @GetMapping(value = "page", name = "参数配置管理-分页")
    public Result page(SysConfigEntity sysConfigEntity) {
        PageResult<SysConfigEntity> page = configService.page(sysConfigEntity);
        return Result.ok().put(page);
    }

    @SaCheckPermission("system:config:query")
    @GetMapping(value = "{id}", name = "参数配置管理-查询id信息")
    public Result getInfo(@PathVariable("id") Long id) {
        SysConfigEntity entity = configService.selectConfigById(id);
        return Result.ok().put(entity);
    }

    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey:.+}")
    public Result getConfigKey(@PathVariable String configKey) {
        return Result.ok(configService.selectConfigByKey(configKey));
    }

    @SaCheckPermission("system:config:add")
    @PostMapping(name = "参数配置管理-新增")
    public Result add(@Validated @RequestBody SysConfigEntity config) {
        if (!configService.checkConfigKeyUnique(config)) {
            return Result.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        return Result.ok(configService.insertConfig(config));

    }

    @SaCheckPermission("system:config:edit")
    @PutMapping(name = "参数配置管理-修改")
    public Result edit(@Validated @RequestBody SysConfigEntity config) {
        if (!configService.checkConfigKeyUnique(config)) {
            return Result.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        return Result.ok(configService.updateConfig(config));

    }

    @SaCheckPermission("system:config:remove")
    @DeleteMapping(value = "/{configIds}", name = "参数配置管理-删除")
    public Result remove(@PathVariable Long[] configIds) {
        configService.deleteConfigByIds(configIds);
        return Result.ok();
    }

    /**
     * 刷新参数缓存
     */
    @SaCheckPermission("system:config:remove")
    @DeleteMapping(value = "/refreshCache", name = "参数配置管理-刷新缓存")
    public Result refreshCache() {
        configService.resetConfigCache();
        return Result.ok();
    }
}
