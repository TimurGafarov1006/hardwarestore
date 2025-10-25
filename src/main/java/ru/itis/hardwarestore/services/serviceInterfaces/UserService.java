package ru.itis.hardwarestore.services.serviceInterfaces;

import ru.itis.hardwarestore.models.User;

public interface UserService {
    User getUser(String userId);
}
