package ru.itis.hardwarestore.services.serviceImpl;

import org.apache.commons.codec.digest.DigestUtils;
import ru.itis.hardwarestore.exceptions.LoginValidateException;
import ru.itis.hardwarestore.exceptions.RegistrationValidateException;
import ru.itis.hardwarestore.models.User;
import ru.itis.hardwarestore.models.UserRole;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.SessionRepository;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.UserRepository;
import ru.itis.hardwarestore.services.serviceInterfaces.SecurityService;
import ru.itis.hardwarestore.utils.PhoneUtils;
import ru.itis.hardwarestore.utils.PropertiesUtil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final Duration sessionDuration;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z]).{8,}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+7|8)?\\s*\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$");

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");


    public SecurityServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
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

        validateUser(firstName, lastName, password, normalizedPhone, email);

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


    private void validateUser(String firstName, String lastName, String password,
                              String normalizedPhone, String email)
    {
        validateNameAndPassword(firstName, lastName, password);
        validatePhone(normalizedPhone);
        validateEmail(email);
    }

    private void validateNameAndPassword(String firstName, String lastName, String password) {
        if (firstName.length() > 2 && lastName.length() > 2) {
            if (!PASSWORD_PATTERN.matcher(password).matches()) {
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

    private void validatePhone(String normalizedPhone) {
        if (userRepository.findByPhone(normalizedPhone).isEmpty()) {
            if (!PHONE_PATTERN.matcher(normalizedPhone).matches()) {
                throw new RegistrationValidateException("Incorrect phone number format");
            }
        } else {
            throw new RegistrationValidateException("The phone is busy");
        }
    }

    private void validateEmail(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                throw new RegistrationValidateException("Incorrect email format");
            }
        } else {
            throw new RegistrationValidateException("The email is busy");
        }
    }
}
