package com.zhengcheng.magic.controller.facade.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zhengcheng.magic.controller.facade.ISocialUserFacade;
import com.zhengcheng.magic.controller.facade.internal.assembler.SocialUserAssembler;
import com.zhengcheng.magic.domain.entity.SocialUser;
import com.zhengcheng.magic.service.ISocialUserService;

import cn.hutool.core.collection.CollectionUtil;
import me.zhyd.oauth.model.AuthUser;

/**
 * 社会化用户表(SocialUser)外观模式，接口实现
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 16:57:02
 */
@Service
public class SocialUserFacadeImpl implements ISocialUserFacade {

    @Autowired
    private ISocialUserService socialUserService;
    @Autowired
    private SocialUserAssembler socialUserAssembler;

    @Transactional
    @Override
    public AuthUser add(AuthUser authUser) {
        SocialUser socialUser = socialUserAssembler.toEntity(authUser);

        socialUser.setOpenId(Optional.ofNullable(authUser.getToken().getOpenId()).orElse(""));
        socialUser.setUid(Optional.ofNullable(authUser.getToken().getUid()).orElse(""));
        socialUser.setAccessCode(Optional.ofNullable(authUser.getToken().getAccessCode()).orElse(""));
        socialUser.setUnionId(Optional.ofNullable(authUser.getToken().getUnionId()).orElse(""));
        socialUser.setScope(Optional.ofNullable(authUser.getToken().getScope()).orElse(""));
        socialUser.setTokenType(Optional.ofNullable(authUser.getToken().getTokenType()).orElse(""));
        socialUser.setIdToken(Optional.ofNullable(authUser.getToken().getIdToken()).orElse(""));
        socialUser.setMacAlgorithm(Optional.ofNullable(authUser.getToken().getMacAlgorithm()).orElse(""));
        socialUser.setMacKey(Optional.ofNullable(authUser.getToken().getMacKey()).orElse(""));
        socialUser.setCode(Optional.ofNullable(authUser.getToken().getCode()).orElse(""));
        socialUser.setOauthToken(Optional.ofNullable(authUser.getToken().getOauthToken()).orElse(""));
        socialUser.setOauthTokenSecret(Optional.ofNullable(authUser.getToken().getOauthTokenSecret()).orElse(""));
        socialUser.setAccessToken(Optional.ofNullable(authUser.getToken().getAccessToken()).orElse(""));
        socialUser.setExpireIn(Optional.of(authUser.getToken().getExpireIn()).orElse(0));
        socialUser.setRefreshToken(Optional.ofNullable(authUser.getToken().getRefreshToken()).orElse(""));

        List<SocialUser> socialUsers = socialUserService.list(new LambdaQueryWrapper<SocialUser>()
            .eq(SocialUser::getUuid, socialUser.getUuid()).eq(SocialUser::getSource, socialUser.getSource()));
        if (CollectionUtil.isNotEmpty(socialUsers)) {
            socialUserService.update(socialUser, new LambdaUpdateWrapper<SocialUser>()
                .eq(SocialUser::getUuid, socialUser.getUuid()).eq(SocialUser::getSource, socialUser.getSource()));
        } else {
            socialUserService.save(socialUser);
        }
        return authUser;
    }

}
