package ru.itis.hardwarestore.exceptions.user;

import ru.itis.hardwarestore.exceptions.UserException;

public class ProductNotFoundException extends UserException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
