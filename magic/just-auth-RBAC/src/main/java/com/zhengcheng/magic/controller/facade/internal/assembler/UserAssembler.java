package com.zhengcheng.magic.controller.facade.internal.assembler;

import java.util.List;

import com.zhengcheng.magic.controller.command.UserCommand;
import com.zhengcheng.magic.controller.facade.internal.dto.UserDTO;
import com.zhengcheng.magic.domain.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * 用户(User)封装类
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserAssembler {

    @Mappings({@Mapping(target = "lastLogin", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "mobile", expression = "java(cn.hutool.core.util.DesensitizedUtil.mobilePhone(user.getMobile()))"),
            @Mapping(target = "email", expression = "java(cn.hutool.core.util.DesensitizedUtil.email(user.getEmail()))"),
    })
    UserDTO toDTO(User user);

    User toEntity(UserCommand userCommand);

    List<UserDTO> toDTOs(List<User> users);
}