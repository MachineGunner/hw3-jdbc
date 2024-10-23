package org.olha_b.service;

import org.olha_b.model.UserRegistrationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserValidatorTest {
    private UserValidator userValidator;
    private UserRegistrationDto userRegistrationDto;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
        userRegistrationDto = new UserRegistrationDto();
    }

    @Test
    void testValidEmail() {
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setRepeatPassword("password");
        userRegistrationDto.setPhoneNumber("+1234567890");
        Assertions.assertFalse(userValidator.isValid(userRegistrationDto));
        userRegistrationDto.setEmail("test");
        Assertions.assertFalse(userValidator.isValid(userRegistrationDto));
        userRegistrationDto.setEmail("test@test.test");
        Assertions.assertTrue(userValidator.isValid(userRegistrationDto));
        userRegistrationDto.setEmail("");
        Assertions.assertFalse(userValidator.isValid(userRegistrationDto));
    }

    @Test
    void testValidPhoneNumber() {
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setRepeatPassword("password");
        userRegistrationDto.setEmail("test@test.test");
        Assertions.assertTrue(userValidator.isValid(userRegistrationDto));
        userRegistrationDto.setPhoneNumber("test");
        Assertions.assertFalse(userValidator.isValid(userRegistrationDto));
        userRegistrationDto.setPhoneNumber("+1234567890");
        Assertions.assertTrue(userValidator.isValid(userRegistrationDto));
    }

    @Test
    void testValidPassword() {
        userRegistrationDto.setEmail("test@test.test");
        userRegistrationDto.setPhoneNumber("+1234567890");
        Assertions.assertFalse(userValidator.isValid(userRegistrationDto));
        userRegistrationDto.setRepeatPassword("password");
        Assertions.assertFalse(userValidator.isValid(userRegistrationDto));
        userRegistrationDto.setRepeatPassword(null);
        userRegistrationDto.setPassword("password");
        Assertions.assertFalse(userValidator.isValid(userRegistrationDto));
        userRegistrationDto.setRepeatPassword("wrong_password");
        Assertions.assertFalse(userValidator.isValid(userRegistrationDto));
        userRegistrationDto.setRepeatPassword("password");
        Assertions.assertTrue(userValidator.isValid(userRegistrationDto));
        userRegistrationDto.setPassword("");
        userRegistrationDto.setRepeatPassword("");
        Assertions.assertFalse(userValidator.isValid(userRegistrationDto));
    }
}