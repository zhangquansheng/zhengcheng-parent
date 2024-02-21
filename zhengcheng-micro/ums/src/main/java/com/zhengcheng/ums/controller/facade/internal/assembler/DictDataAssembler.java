package com.zhengcheng.ums.controller.facade.internal.assembler;

import java.util.List;

import com.zhengcheng.ums.domain.entity.DictData;
import com.zhengcheng.ums.dto.DictDataDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * DictDataAssembler
 *
 * @author quansheng1.zhang
 * @since 2022/4/29 15:08
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface DictDataAssembler {

    @Mappings({ @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss"), })
    DictDataDTO toDTO(DictData dictData);

    List<DictDataDTO> toDTOs(List<DictData> dictDatas);

}
