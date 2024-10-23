package org.olha_b.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.olha_b.model.User;
import org.olha_b.model.UserRegistrationDto;
import org.olha_b.model.UserResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.olha_b.repository.UserRepository;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldRegisterUserSuccessfully() {
        mockRegistration();
        UserRegistrationDto userRegistrationDto = createValidUser();
        UserResponseDto userResponseDto = userService.registerUser(userRegistrationDto);

        Assertions.assertNotNull(userResponseDto);
        Assertions.assertEquals(userRegistrationDto.getPhoneNumber(), userResponseDto.getPhoneNumber());
        Assertions.assertEquals(userRegistrationDto.getEmail(), userResponseDto.getEmail());
        Assertions.assertNotNull(userResponseDto.getId());
    }

    @Test
    void shouldReturnNullForInvalidUser() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        Assertions.assertNull(userService.registerUser(userRegistrationDto));
    }

    @Test
    void shouldReturnUserById() {
        User userCreated = mockRegistration();
        when(userRepository.getById(1L)).thenReturn(Optional.of(userCreated));

        UserRegistrationDto userRegistrationDto = createValidUser();
        UserResponseDto userResponseDtoRegister = userService.registerUser(userRegistrationDto);
        Assertions.assertNotNull(userResponseDtoRegister);
        UserResponseDto userResponseDtoGet = userService.getUserById(userResponseDtoRegister.getId());

        Assertions.assertNotNull(userResponseDtoGet);
        Assertions.assertEquals(userRegistrationDto.getPhoneNumber(), userResponseDtoGet.getPhoneNumber());
        Assertions.assertEquals(userRegistrationDto.getEmail(), userResponseDtoGet.getEmail());
        Assertions.assertEquals(userResponseDtoRegister.getId(), userResponseDtoGet.getId());
    }

    @Test
    void shouldReturnNullForInvalidUserId() {
        when(userRepository.getById(-1L)).thenReturn(Optional.empty());
        Assertions.assertNull(userService.getUserById(-1L));
    }

    private UserRegistrationDto createValidUser() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setRepeatPassword("password");
        userRegistrationDto.setPhoneNumber("+1234567890");
        userRegistrationDto.setEmail("test@test.test");
        return userRegistrationDto;
    }

    private User mockRegistration() {
        User user = new User();
        user.setPhoneNumber("+1234567890");
        user.setEmail("test@test.test");
        User userCreated = new User(1L, "test@test.test", "+1234567890");
        when(userRepository.create(user)).thenReturn(userCreated);
        return userCreated;
    };
}