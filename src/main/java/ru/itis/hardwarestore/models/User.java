package ru.itis.hardwarestore.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class User {
    private String id;
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
