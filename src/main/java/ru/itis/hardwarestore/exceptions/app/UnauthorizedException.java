package ru.itis.hardwarestore.exceptions.app;

import ru.itis.hardwarestore.exceptions.AppException;

public class UnauthorizedException extends AppException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
