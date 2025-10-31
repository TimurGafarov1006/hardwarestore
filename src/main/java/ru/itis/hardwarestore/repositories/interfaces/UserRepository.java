package ru.itis.hardwarestore.repositories.interfaces;

import ru.itis.hardwarestore.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    void update(User editedUser);
    void delete(String id);
    List<User> findAll();
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
