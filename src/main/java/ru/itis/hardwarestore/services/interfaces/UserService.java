package ru.itis.hardwarestore.services.interfaces;

import ru.itis.hardwarestore.models.User;

import java.time.LocalDate;
import java.util.UUID;

public interface UserService {
    User getUser(UUID userId);
    void updateUser(User oldUser, String firstName, String lastName,
                    String password, String phone, String email, LocalDate birthday);
}
