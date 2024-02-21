package com.zhengcheng.ums.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.ums.domain.entity.Authority;
import com.zhengcheng.ums.domain.mapper.AuthorityMapper;
import com.zhengcheng.ums.service.AuthorityService;
import com.zhengcheng.common.exception.BizException;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 权限表(Authority)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:57
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;
    /**
     * 权限最大的层级
     */
    public final int        AUTHORITY_MAX_LEVEL = 3;

    @Override
    public boolean save(Authority authority) {
        if (StrUtil.isNotBlank(authority.getCode())) {
            List<Authority> codeAuthorities = authorityMapper
                    .selectList(new LambdaQueryWrapper<Authority>().eq(Authority::getCode, authority.getCode()));
            if (CollectionUtil.isNotEmpty(codeAuthorities)) {
                throw new BizException(StrUtil.format("已存在 code : [{}] 对应的权限！", authority.getCode()));
            }
        }

        authority.setCreateUserId(authority.getUpdateUserId());
        authority.setEnable(Boolean.TRUE);
        // 设置树路径和层级
        if (Objects.isNull(authority.getPid()) || authority.getPid() <= 0) {
            authority.setPid(0L);
            authority.setLevel(1);
        } else {
            Authority pAuthority = authorityMapper.selectById(authority.getPid());
            if (Objects.isNull(pAuthority)) {
                throw new BizException(StrUtil.format("不存在 pId:[{}] 对应的权限！", authority.getPid()));
            }
            if (pAuthority.getLevel() + 1 > AUTHORITY_MAX_LEVEL) {
                throw new BizException(StrUtil.format("已经超过最大层级， maxLevel：[{}]", AUTHORITY_MAX_LEVEL));
            }

            authority.setLevel(pAuthority.getLevel() + 1);
        }

        return authorityMapper.insert(authority) > 0;
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return authorityMapper.getPermissionList(loginId);
    }

    @Override
    public List<Authority> getAuthorityList(Long userId) {
        return authorityMapper.getAuthorityList(userId);
    }

    @Override
    public List<Authority> getAuthoritiesByRoleId(Long roleId) {
        return authorityMapper.getAuthoritiesByRoleId(roleId);
    }
}
