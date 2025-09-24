package it.exercise.exercise_app.mapper;

import it.exercise.exercise_app.dto.SubscriptionDetailDTO;
import it.exercise.exercise_app.entity.SubscriptionDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionDetailMapper {

    SubscriptionDetailDTO toDTO(SubscriptionDetail detail);
    SubscriptionDetail toEntity(SubscriptionDetailDTO dto);
}
