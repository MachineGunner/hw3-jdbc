package org.olha_b.service;

import org.olha_b.model.UserRegistrationDto;
import org.olha_b.model.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationDto userRegistrationDto);

    UserResponseDto getUserById(Long userId);
}
