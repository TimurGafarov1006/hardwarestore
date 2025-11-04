package ru.itis.hardwarestore.repositories.interfaces;

import ru.itis.hardwarestore.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    void update(User editedUser);
    void delete(UUID id);
    List<User> findAll();
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
