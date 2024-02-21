package com.zhengcheng.magic.controller.facade;

import me.zhyd.oauth.model.AuthUser;

/**
 * 社会化用户表(SocialUser)表Facade接口
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 16:57:02
 */
public interface ISocialUserFacade {

    AuthUser add(AuthUser authUser);

}
