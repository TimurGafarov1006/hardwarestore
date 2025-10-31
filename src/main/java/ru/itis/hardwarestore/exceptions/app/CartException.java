package ru.itis.hardwarestore.exceptions.app;

import ru.itis.hardwarestore.exceptions.AppException;

public class CartException extends AppException {
    public CartException(String message) {
        super(message);
    }
}
