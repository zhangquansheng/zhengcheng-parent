package com.zhengcheng.magic.controller.facade.internal.assembler;

import com.zhengcheng.magic.controller.command.UserAuthCommand;
import com.zhengcheng.magic.controller.facade.internal.dto.UserAuthDTO;
import com.zhengcheng.magic.domain.entity.UserAuth;
import org.mapstruct.Mapper;
import org.mapstruct.Builder;
import java.util.List;

/**
 * 用户授权表(UserAuth)封装类
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 17:28:11
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserAuthAssembler {

    UserAuthDTO toDTO(UserAuth userAuth);

    UserAuth toEntity(UserAuthCommand userAuthCommand);

    List<UserAuthDTO> toDTOs(List<UserAuth> userAuths);
}
