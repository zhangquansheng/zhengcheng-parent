package com.zhengcheng.ums.controller.admin.system;

import com.zhengcheng.common.domain.Result;
import com.zhengcheng.ums.common.constant.UserConstants;
import com.zhengcheng.ums.domain.entity.SysMenuEntity;
import com.zhengcheng.ums.service.SysMenuService;

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

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpUtil;

@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    private Long getUserId() {
        return Long.parseLong(String.valueOf(StpUtil.getLoginId()));
    }

    /**
     * 获取菜单列表
     */
    @GetMapping(value = "/list", name = "菜单管理-分页")
    public Result list(SysMenuEntity menu) {
        List<SysMenuEntity> menus = menuService.selectMenuList(menu, getUserId());
        return Result.ok(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}", name = "菜单管理-查询")
    public Result getInfo(@PathVariable Long menuId) {
        return Result.ok(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping(value = "/treeselect", name = "菜单管理-获取菜单下拉树列表")
    public Result treeSelect(SysMenuEntity menu) {
        List<SysMenuEntity> menus = menuService.selectMenuList(menu, getUserId());
        return Result.ok(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}", name = "菜单管理-加载对应角色菜单列表树")
    public Result roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysMenuEntity> menus = menuService.selectMenuList(getUserId());
        Result ajax = Result.ok();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    /**
     * 新增菜单
     */
//    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @PostMapping(name = "菜单管理-新增")
    public Result add(@Validated @RequestBody SysMenuEntity menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return Result.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !HttpUtil.isHttp(menu.getPath())) {
            return Result.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        return Result.ok(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @PutMapping(name = "菜单管理-修改")
    public Result edit(@Validated @RequestBody SysMenuEntity menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return Result.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !HttpUtil.isHttp(menu.getPath())) {
            return Result.error("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return Result.error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        return Result.ok(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @DeleteMapping(value = "/{menuId}", name = "菜单管理-删除")
    public Result remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return Result.error("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return Result.error("菜单已分配,不允许删除");
        }
        return Result.ok(menuService.deleteMenuById(menuId));
    }


}
