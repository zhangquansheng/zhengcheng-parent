package com.zhengcheng.magic.controller;

import com.zhengcheng.common.validation.annotation.Update;
import com.zhengcheng.common.utils.Result;
import com.zhengcheng.magic.controller.command.AuthorityCommand;
import com.zhengcheng.magic.controller.facade.IAuthorityFacade;
import com.zhengcheng.magic.controller.facade.internal.dto.AuthorityDTO;
import com.zhengcheng.magic.controller.facade.internal.dto.TreeNodeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限表(Authority)控制层
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:58
 */
@Api(tags = {"权限表(Authority)接口"})
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private IAuthorityFacade authorityFacade;

    @ApiOperation("查询权限树")
    @GetMapping("/tree")
    public Result<List<TreeNodeDTO>> tree() {
        return Result.successData(authorityFacade.findTree());
    }

    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/{id}")
    public Result<AuthorityDTO> findById(@PathVariable("id") Long id) {
        return Result.successData(authorityFacade.findById(id));
    }

    @ApiOperation("添加单条数据")
    @PostMapping("/add")
    public Result<Long> add(@Validated @RequestBody AuthorityCommand authorityCommand) {
        return Result.successData(authorityFacade.add(authorityCommand));
    }

    @ApiOperation("更新单条数据")
    @PostMapping("/update")
    public Result<Long> update(@Validated({Update.class}) @RequestBody AuthorityCommand authorityCommand) {
        return Result.successData(authorityFacade.update(authorityCommand));
    }

}
