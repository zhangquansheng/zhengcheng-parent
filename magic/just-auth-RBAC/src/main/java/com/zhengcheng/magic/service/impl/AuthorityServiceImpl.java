package com.zhengcheng.magic.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.magic.domain.entity.Authority;
import com.zhengcheng.magic.domain.mapper.AuthorityMapper;
import com.zhengcheng.magic.service.IAuthorityService;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 权限表(Authority)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:57
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements IAuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;
    @Value("${authority.max.level:3}")
    private Integer maxLevel;

    @Override
    public boolean save(Authority authority) {
        List<Authority> codeAuthorities =
            authorityMapper.selectList(new LambdaQueryWrapper<Authority>().eq(Authority::getCode, authority.getCode()));
        if (CollectionUtil.isNotEmpty(codeAuthorities)) {
            throw new BizException(StrUtil.format("已存在 code : [{}] 对应的权限！", authority.getCode()));
        }

        // 设置树路径和层级
        if (Objects.isNull(authority.getPid()) || authority.getPid() <= 0) {
            authority.setTreePath(",");
            authority.setLevel(1);
        } else {
            Authority pAuthority = authorityMapper.selectById(authority.getPid());
            if (Objects.isNull(pAuthority)) {
                throw new BizException(StrUtil.format("不存在 pId:[{}] 对应的权限！", authority.getPid()));
            }
            if (pAuthority.getLevel() + 1 > maxLevel) {
                throw new BizException(StrUtil.format("已经超过最大层级， maxLevel：[{}]", maxLevel));
            }

            authority.setLevel(pAuthority.getLevel() + 1);
            authority.setTreePath(StrUtil.format("{}{},", pAuthority.getTreePath(), pAuthority.getId()));
        }

        return authorityMapper.insert(authority) > 0;
    }
}
