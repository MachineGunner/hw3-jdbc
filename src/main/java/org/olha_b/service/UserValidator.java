package org.olha_b.service;

import org.olha_b.model.UserRegistrationDto;

public class UserValidator {
    private static final String PHONE_NUMBER_PATTERN = "\\+?\\d{10,15}";

    public boolean isValid(UserRegistrationDto userDto) {
        return isEmailValid(userDto.getEmail())
                && isPasswordValid(userDto.getPassword(), userDto.getRepeatPassword())
                && isPhoneNumberValid(userDto.getPhoneNumber());
    }

    private boolean isEmailValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            return phoneNumber.matches(PHONE_NUMBER_PATTERN);
        }
        return true;
    }

    private boolean isPasswordValid(String password, String repeatPassword) {
        if (password == null || repeatPassword == null) {
            return false;
        }
        return !password.isEmpty() && password.equals(repeatPassword);
    }
}
