package com.post.hub.utilsservice.mapper;

import com.post.hub.utilsservice.kafka.model.UtilMessage;
import com.post.hub.utilsservice.model.dto.ActionLogDTO;
import com.post.hub.utilsservice.model.entity.ActionLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ActionLogMapper {

    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isRead", expression = "java(false)")
    @Mapping(target = "id", ignore = true)
    ActionLog mapKafkaMessageToEntity(UtilMessage message);

    ActionLogDTO toDTO(ActionLog log);

}
