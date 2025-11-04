package ru.itis.hardwarestore.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.itis.hardwarestore.models.enums.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class User {
    private UUID id;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private String salt;
    private String phone;
    private String email;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
