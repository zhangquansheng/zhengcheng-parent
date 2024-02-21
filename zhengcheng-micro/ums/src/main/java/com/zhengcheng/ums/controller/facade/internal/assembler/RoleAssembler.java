package com.zhengcheng.ums.controller.facade.internal.assembler;

import java.util.List;

import com.zhengcheng.ums.domain.entity.Role;
import com.zhengcheng.ums.dto.command.RoleCommand;
import com.zhengcheng.ums.dto.RoleDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * 角色表(Role)封装类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface RoleAssembler {

    @Mappings({@Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss"),})
    RoleDTO toDTO(Role role);

    Role toEntity(RoleCommand roleCommand);

    List<RoleDTO> toDTOs(List<Role> roles);
}
