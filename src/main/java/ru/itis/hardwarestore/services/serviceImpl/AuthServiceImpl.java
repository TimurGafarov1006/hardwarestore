package ru.itis.hardwarestore.services.serviceImpl;

import org.apache.commons.codec.digest.DigestUtils;
import ru.itis.hardwarestore.exceptions.user.LoginValidateException;
import ru.itis.hardwarestore.models.User;
import ru.itis.hardwarestore.models.enums.UserRole;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.SessionRepository;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.UserRepository;
import ru.itis.hardwarestore.services.serviceInterfaces.AuthService;
import ru.itis.hardwarestore.utils.PhoneUtils;
import ru.itis.hardwarestore.utils.PropertiesUtil;
import ru.itis.hardwarestore.utils.ValidationUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final Duration sessionDuration;


    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;

        Properties properties = PropertiesUtil.getProperties();
        sessionDuration = Duration.ofMinutes(Integer.parseInt(properties.getProperty("sessionDuration")));
    }

    @Override
    public String registerUser(String firstName, String lastName, String password, String phone,
                               String email, LocalDate birthday)
    {
        String normalizedPhone = PhoneUtils.normalizePhone(phone);

        ValidationUtils.validateUser(firstName, lastName, password, normalizedPhone, email, false);

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
                normalizedPhone,
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

    @Override
    public String loginUser(String login, String password) {
        Optional<User> userOptional = login.contains("@")
                ? userRepository.findByEmail(login)
                : userRepository.findByPhone(PhoneUtils.normalizePhone(login));

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            String salt = user.getSalt();
            String userPasswordHash = user.getPasswordHash();

            if (DigestUtils.sha256Hex(password + salt).equals(userPasswordHash)) {
                String sessionId = UUID.randomUUID().toString();
                sessionRepository.addSession(
                        sessionId,
                        user.getId(),
                        LocalDateTime.now().plus(sessionDuration)
                );

                return sessionId;
            } else {
                throw new LoginValidateException("Incorrect email or password");
            }
        }

        throw new LoginValidateException("Incorrect email or password");
    }
}
