package com.zhengcheng.ums.controller.admin.monitor;


import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.common.domain.Result;
import com.zhengcheng.ums.domain.entity.SysLoginLogEntity;
import com.zhengcheng.ums.service.SysLoginLogService;
import com.zhengcheng.ums.service.SysPasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;


/**
 * 系统访问记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLogininforController {
    @Autowired
    private SysLoginLogService logininforService;
    @Autowired
    private SysPasswordService passwordService;

    @SaCheckPermission("monitor:logininfor:list")
    @GetMapping(value = "/list", name = "登录日志-分类列表")
    public Result list(SysLoginLogEntity logininfor) {
        PageResult<SysLoginLogEntity> page = logininforService.selectLogininforPage(logininfor);
        return Result.ok().put(page);
    }

    @SaCheckPermission("monitor:logininfor:remove")
    @DeleteMapping(value = "/{infoIds}", name = "登录日志-删除")
    public Result remove(@PathVariable Long[] infoIds) {
        return Result.ok(logininforService.deleteLogininforByIds(infoIds));
    }

    @SaCheckPermission("monitor:logininfor:remove")
    @DeleteMapping(value = "/clean", name = "登录日志-清空")
    public Result clean() {
        logininforService.cleanLogininfor();
        return Result.ok();
    }

    @SaCheckPermission("monitor:logininfor:unlock")
    @GetMapping(value = "/unlock/{userName}", name = "登录日志-解锁用户")
    public Result unlock(@PathVariable("userName") String userName) {
        passwordService.clearLoginRecordCache(userName);
        return Result.ok();
    }
}
