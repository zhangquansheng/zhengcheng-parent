package com.zhengcheng.ums.controller.facade.internal.assembler;

import java.util.List;

import com.zhengcheng.ums.domain.entity.LogRecord;
import com.zhengcheng.ums.dto.LogRecordDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * LogRecordAssembler
 *
 * @author quansheng1.zhang
 * @since 2022/4/30 21:35
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface LogRecordAssembler {

    @Mappings({ @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss"), })
    LogRecordDTO toDTO(LogRecord logRecord);

    List<LogRecordDTO> toDTOs(List<LogRecord> logRecords);
}
