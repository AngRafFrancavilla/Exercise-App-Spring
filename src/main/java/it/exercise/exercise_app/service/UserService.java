package it.exercise.exercise_app.service;

import it.exercise.exercise_app.dto.UserDTO;
import it.exercise.exercise_app.entity.User;
import it.exercise.exercise_app.mapper.UserMapper;
import it.exercise.exercise_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO createUser(User user) {
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElse(null);
    }
}
