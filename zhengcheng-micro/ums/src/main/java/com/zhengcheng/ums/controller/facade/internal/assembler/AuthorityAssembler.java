package com.zhengcheng.ums.controller.facade.internal.assembler;

import java.util.List;

import com.zhengcheng.ums.domain.entity.Authority;
import com.zhengcheng.ums.dto.command.AuthorityCommand;
import com.zhengcheng.ums.dto.AuthorityDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * 权限表(Authority)封装类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:58
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AuthorityAssembler {

    @Mappings({ @Mapping(target = "type", source = "type.value"),
            @Mapping(target = "disabled", expression = "java(!authority.getEnable())"), })
    AuthorityDTO toDTO(Authority authority);

    @Mappings({
            @Mapping(target = "type", expression = "java(com.zhengcheng.ums.domain.enums.AuthorityTypeEnum.getByValue(authorityCommand.getType()))"), })
    Authority toEntity(AuthorityCommand authorityCommand);

    List<AuthorityDTO> toDTOs(List<Authority> authoritys);
}
