package it.exercise.exercise_app.mapper;

import it.exercise.exercise_app.dto.UserDTO;
import it.exercise.exercise_app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SubscriptionMapper.class})
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);
}
