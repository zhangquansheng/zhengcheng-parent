package com.zhengcheng.magic.controller.facade.internal.assembler;

import com.zhengcheng.magic.controller.command.UserRoleCommand;
import com.zhengcheng.magic.controller.facade.internal.dto.UserRoleDTO;
import com.zhengcheng.magic.domain.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Builder;
import java.util.List;

/**
 * 用户角色表(UserRole)封装类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:26:58
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserRoleAssembler {

    UserRoleDTO toDTO(UserRole userRole);

    UserRole toEntity(UserRoleCommand userRoleCommand);

    List<UserRoleDTO> toDTOs(List<UserRole> userRoles);
}
