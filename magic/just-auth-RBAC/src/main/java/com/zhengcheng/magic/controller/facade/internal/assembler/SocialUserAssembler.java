package com.zhengcheng.magic.controller.facade.internal.assembler;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.zhengcheng.magic.domain.entity.SocialUser;

import me.zhyd.oauth.model.AuthUser;

/**
 * 社会化用户表(SocialUser)封装类
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 16:57:02
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SocialUserAssembler {

    AuthUser toAuthUser(SocialUser socialUser);

    SocialUser toEntity(AuthUser authUser);

    List<AuthUser> toAuthUsers(List<SocialUser> socialUsers);
}
