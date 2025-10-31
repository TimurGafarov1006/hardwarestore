package ru.itis.hardwarestore.exceptions.user;

import ru.itis.hardwarestore.exceptions.UserException;

public class LoginValidateException extends UserException {
    public LoginValidateException(String message) {
        super(message);
    }
}
