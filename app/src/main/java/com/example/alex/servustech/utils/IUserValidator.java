package com.example.alex.servustech.utils;

import com.example.alex.servustech.exceptions.InvalidCredentials;
import com.example.alex.servustech.model.User;

public interface IUserValidator {
    /* Strings regarding different errors*/
    String EMAIL_IS_EMPTY = "Email cannot be empty!\n";
    String PASSWORD_IS_EMPTY = "Password cannot be empty!\n";
    String CONFIRM_PASS_IS_EMPTY = "You must enter the password again!\n";
    String EMAIL_NOT_VALID = "Please enter a valid email!\n";
    String PASSWORDS_DO_NOT_MATCH = "Password do not match!\n";



    void validate(User user, String confirmPassword) throws InvalidCredentials;
}
