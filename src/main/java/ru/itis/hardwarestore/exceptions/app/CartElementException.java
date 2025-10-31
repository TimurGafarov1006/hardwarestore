package ru.itis.hardwarestore.exceptions.app;

import ru.itis.hardwarestore.exceptions.AppException;

public class CartElementException extends AppException {
    public CartElementException(String message) {
        super(message);
    }
}
