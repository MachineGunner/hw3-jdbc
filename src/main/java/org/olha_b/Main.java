package org.olha_b;

import org.olha_b.model.UserRegistrationDto;
import org.olha_b.model.UserResponseDto;
import org.olha_b.repository.impl.UserRepositoryImpl;
import org.olha_b.service.UserServiceImpl;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("test@test.test");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setRepeatPassword("password");
        userRegistrationDto.setPhoneNumber("+1234567890");
        UserServiceImpl userService = new UserServiceImpl(new UserRepositoryImpl());
        UserResponseDto userResponseDto = userService.registerUser(userRegistrationDto);
        System.out.println(userService.getUserById(userResponseDto.getId()));
    }
}
