package ru.itis.hardwarestore.exceptions.user;

import ru.itis.hardwarestore.exceptions.UserException;

public class RegistrationValidateException extends UserException {
    public RegistrationValidateException(String message) {
        super(message);
    }
}
