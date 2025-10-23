package ru.itis.hardwarestore.exceptions;

public class LoginValidateException extends RuntimeException {
    public LoginValidateException(String message) {
        super(message);
    }
}
