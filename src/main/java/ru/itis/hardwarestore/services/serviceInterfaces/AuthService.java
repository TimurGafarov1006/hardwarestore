package ru.itis.hardwarestore.services.serviceInterfaces;

import java.time.LocalDate;

public interface AuthService {
    String registerUser(String firstName, String lastName, String password, String phone,
                        String email, LocalDate birthday);
    String loginUser(String login, String password);
}
