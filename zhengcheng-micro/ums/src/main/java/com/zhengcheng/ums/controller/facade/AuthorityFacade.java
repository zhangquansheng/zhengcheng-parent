package com.zhengcheng.ums.controller.facade;

import java.util.List;

import com.zhengcheng.ums.dto.command.AuthorityCommand;
import com.zhengcheng.ums.dto.command.EnableCommand;
import org.springframework.lang.Nullable;

import com.zhengcheng.ums.dto.AuthorityDTO;


/**
 * 权限表(Authority)表Facade接口
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:58
 */
public interface AuthorityFacade {

    /**
     * 查询所有权限
     */
    List<AuthorityDTO> findAll();

    /**
     * 根据上级ID查询权限列表
     * @param pid
     *            上级ID
     */
    List<AuthorityDTO> findByPid(@Nullable Long pid);

    /**
     * 通过ID查询单条数据
     *
     * @param id
     *            主键
     * @return AuthorityDTO
     */
    AuthorityDTO findById(Long id);

    /**
     * 添加单条数据
     *
     * @param authorityCommand
     *            数据查询对象
     */
    Long save(AuthorityCommand authorityCommand);

    /**
     * 更新单条数据
     *
     * @param authorityCommand
     *            数据查询对象
     */
    boolean update(AuthorityCommand authorityCommand);

    /**
     * 开启/禁用
     * @param enableCommand EnableCommand
     */
    boolean enable(EnableCommand enableCommand);

    /**
     * 根据ID删除
     * @param id ID
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据角色查询权限列表
     * @param roleId 角色ID
     * @return TreeDTO
     */
    List<AuthorityDTO> findByRoleId(Long roleId);

}
