package ru.itis.hardwarestore.services.serviceImpl;

import org.apache.commons.codec.digest.DigestUtils;
import ru.itis.hardwarestore.exceptions.UnauthorizedException;
import ru.itis.hardwarestore.models.User;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.UserRepository;
import ru.itis.hardwarestore.services.serviceInterfaces.UserService;
import ru.itis.hardwarestore.utils.PhoneUtils;
import ru.itis.hardwarestore.utils.ValidationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public void updateUser(User oldUser, String newFirstName, String newLastName,
                           String newPassword, String newPhone, String newEmail, LocalDate birthday)
    {
        String passwordHash = oldUser.getPasswordHash();
        String salt = oldUser.getSalt();
        String normalizedNewPhone =
                newPhone == null ? null : PhoneUtils.normalizePhone(newPhone);

        if (newPassword != null) {
            salt = UUID.randomUUID().toString();
            passwordHash = DigestUtils.sha256Hex(newPassword + salt);
        }

        ValidationUtils.validateUser(newFirstName, newLastName, newPassword, normalizedNewPhone, newEmail, true);

        User updatedUser = new User(
                oldUser.getId(),
                oldUser.getRole(),
                newFirstName != null ? newFirstName : oldUser.getFirstName(),
                newLastName != null ? newLastName : oldUser.getLastName(),
                passwordHash,
                salt,
                normalizedNewPhone != null ? normalizedNewPhone : oldUser.getPhone(),
                newEmail != null ? newEmail : oldUser.getEmail(),
                birthday != null ? birthday : oldUser.getBirthday(),
                oldUser.getCreatedAt(),
                LocalDateTime.now()
        );

        userRepository.update(updatedUser);
    }
}
