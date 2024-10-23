package org.olha_b.service;

import org.olha_b.model.User;
import org.olha_b.model.UserRegistrationDto;
import org.olha_b.model.UserResponseDto;
import org.olha_b.repository.UserRepository;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto registerUser(UserRegistrationDto userRegistrationDto) {
        UserValidator userValidator = new UserValidator();
        if (userValidator.isValid(userRegistrationDto)) {
            User user = new User();
            user.setEmail(userRegistrationDto.getEmail());
            user.setPhoneNumber(userRegistrationDto.getPhoneNumber());
            User createdUser = userRepository.create(user);
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setEmail(createdUser.getEmail());
            userResponseDto.setPhoneNumber(createdUser.getPhoneNumber());
            userResponseDto.setId(createdUser.getId());
            return userResponseDto;
        }
        return null;
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        Optional<User> user = userRepository.getById(userId);
        if (user.isPresent()) {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setEmail(user.get().getEmail());
            userResponseDto.setPhoneNumber(user.get().getPhoneNumber());
            userResponseDto.setId(user.get().getId());
            return userResponseDto;
        }
        return null;
    }
}
