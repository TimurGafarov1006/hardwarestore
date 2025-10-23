package ru.itis.hardwarestore.exceptions;

public class RegistrationValidateException extends RuntimeException {
    public RegistrationValidateException(String message) {
        super(message);
    }
}
