package ru.itis.hardwarestore.services.interfaces;

import ru.itis.hardwarestore.models.User;

import java.time.LocalDate;

public interface UserService {
    User getUser(String userId);
    void updateUser(User oldUser, String firstName, String lastName,
                    String password, String phone, String email, LocalDate birthday);
}
