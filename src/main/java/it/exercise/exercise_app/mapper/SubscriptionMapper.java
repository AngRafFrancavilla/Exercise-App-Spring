package it.exercise.exercise_app.mapper;

import it.exercise.exercise_app.dto.SubscriptionDTO;
import it.exercise.exercise_app.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionDTO toDTO(Subscription subscription);
    Subscription toEntity(SubscriptionDTO dto);
}


