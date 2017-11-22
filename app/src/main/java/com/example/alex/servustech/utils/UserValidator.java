package com.example.alex.servustech.utils;


import com.example.alex.servustech.Exceptions.InvalidCredentials;
import com.example.alex.servustech.Model.User;
import com.example.alex.servustech.utils.IUserValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements IUserValidator {
    private static final int NUMBER_OF_LETTERS_IN_PASSWORD = 6;
    private static final int NUMBER_OF_DIGITS_IN_PASSWORD = 1;
    private static final String PASSWORD_NOT_VALID = "Password must have " + NUMBER_OF_LETTERS_IN_PASSWORD
            + " letters and " + NUMBER_OF_DIGITS_IN_PASSWORD + " digits!\n";

    private static final int PASSWORD_LENGTH = NUMBER_OF_DIGITS_IN_PASSWORD + NUMBER_OF_LETTERS_IN_PASSWORD;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    public void validate(User user, String confirmPassword) throws InvalidCredentials {
        String email = user.getEmail();
        String password = user.getPassword();
        String errors = "";

        if (isEmpty(email) || stringIsNull(email)) {
            errors += EMAIL_IS_EMPTY;
        }
        if (isEmpty(password) || stringIsNull(password)) {
            errors += PASSWORD_IS_EMPTY;
        }
        if (isEmpty(confirmPassword) || stringIsNull(confirmPassword)) {
            errors += CONFIRM_PASS_IS_EMPTY;
        }

        /* If any of these fields are empty, we do not check further */
        if (errors.length() > 0) {
            throw new InvalidCredentials(errors);
        }

        if (!emailIsValid(email)) {
            errors += EMAIL_NOT_VALID;
        }
        if (!passwordFormatIsAccepted(password)) {
            errors += PASSWORD_NOT_VALID;
        }

        if (!passwordsMatches(password, confirmPassword)) {
            errors += PASSWORDS_DO_NOT_MATCH;
        }


        if (errors.length() > 0) {
            throw new InvalidCredentials(errors);
        }
    }


    private boolean stringIsNull(String word) {
        return word == null;
    }

    private boolean isEmpty(String word) {
        return word.equals("");
    }


    /* We check if the email is a valid one, meaning it has '@', domain etc.
     * To make the check easier, we use a REGEX pattern */
    private boolean emailIsValid(String email) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /* We check the password to contain the given number letters and the given number digits */
    /* Since this is very simple to do without REGEX, we'll do it without regex. */
    private boolean passwordFormatIsAccepted(String password) {
        /* If the password does not meet the required length, we exit the function */
        if (password.length() != PASSWORD_LENGTH) {
            return false;
        }

        int numberOfLetters = 0;
        int numberOfDigits = 0;

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            if (Character.isLetter(password.charAt(i))) {
                numberOfLetters++;
            } else if (Character.isDigit(password.charAt(i))) {
                numberOfDigits++;
            }
        }

        return numberOfDigits == NUMBER_OF_DIGITS_IN_PASSWORD
                && numberOfLetters == NUMBER_OF_LETTERS_IN_PASSWORD;
    }

    /* Checks if the two given words (passwords) are the same or not */
    private boolean passwordsMatches(String password1, String password2) {
        return password1.equals(password2);
    }

}
