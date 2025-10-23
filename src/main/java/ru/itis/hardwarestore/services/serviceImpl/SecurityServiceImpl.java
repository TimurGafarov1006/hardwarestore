package ru.itis.hardwarestore.services.serviceImpl;

import org.apache.commons.codec.digest.DigestUtils;
import ru.itis.hardwarestore.exceptions.RegistrationValidateException;
import ru.itis.hardwarestore.models.User;
import ru.itis.hardwarestore.models.UserRole;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.SessionRepository;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.UserRepository;
import ru.itis.hardwarestore.services.serviceInterfaces.SecurityService;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final Duration sessionDuration;

    public SecurityServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;

        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sessionDuration = Duration.ofMinutes(Integer.parseInt(properties.getProperty("sessionDuration")));
    }

    @Override
    public String registerUser(String firstName, String lastName, String password, String phone,
                               String email, LocalDate birthday)
    {
        validateUser(firstName, lastName, password, phone, email);

        String userId = UUID.randomUUID().toString();
        String salt = UUID.randomUUID().toString();
        String passwordHash = DigestUtils.sha256Hex(password + salt);

        User user = new User(
                userId,
                UserRole.CUSTOMER,
                firstName,
                lastName,
                passwordHash,
                salt,
                phone,
                email,
                birthday,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        userRepository.save(user);

        String sessionId = UUID.randomUUID().toString();
        sessionRepository.addSession(
                sessionId,
                userId,
                LocalDateTime.now().plus(sessionDuration)
        );

        return sessionId;
    }

    private void validateUser(String firstName, String lastName, String password,
                              String phone, String email)
    {
        validateNameAndPassword(firstName, lastName, password);
        validatePhone(phone);
        validateEmail(email);
    }

    private void validateNameAndPassword(String firstName, String lastName, String password) {
        if (firstName.length() > 2 && lastName.length() > 2) {
            if (!password.matches("^(?=.*[A-Z]).{8,}$")) {
                throw new RegistrationValidateException("Incorrect password format");
            }
        } else {
            throw new RegistrationValidateException(
                    """
                    The first name or last name is unrealistic short.\n
                    The length of first name and last name must be at least 2 characters for each
                    """
            );
        }
    }

    private void validatePhone(String phone) {
        if (userRepository.findByPhone(phone).isEmpty()) {
            if (!phone.matches("^(\\+7|8)?\\s*\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$")) {
                throw new RegistrationValidateException("Incorrect phone number format");
            }
        } else {
            throw new RegistrationValidateException("The phone is busy");
        }

    }

    private void validateEmail(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                throw new RegistrationValidateException("Incorrect email format");
            }
        } else {
            throw new RegistrationValidateException("The email is busy");
        }
    }
}
