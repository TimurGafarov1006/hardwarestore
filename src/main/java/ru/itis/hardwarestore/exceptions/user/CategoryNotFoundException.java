package ru.itis.hardwarestore.exceptions.user;

import ru.itis.hardwarestore.exceptions.UserException;

public class CategoryNotFoundException extends UserException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
