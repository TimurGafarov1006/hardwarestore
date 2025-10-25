package ru.itis.hardwarestore.services.serviceImpl;

import ru.itis.hardwarestore.exceptions.UnauthorizedException;
import ru.itis.hardwarestore.models.User;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.UserRepository;
import ru.itis.hardwarestore.services.serviceInterfaces.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UnauthorizedException("Invalid session");
        }
    }
}
