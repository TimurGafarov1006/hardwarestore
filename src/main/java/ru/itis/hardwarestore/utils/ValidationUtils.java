package ru.itis.hardwarestore.utils;

import ru.itis.hardwarestore.exceptions.user.RegistrationValidateException;
import ru.itis.hardwarestore.repositories.repositoryImpl.UserRepositoryJdbc;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.UserRepository;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final UserRepository userRepository = new UserRepositoryJdbc();

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z]).{8,}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+7|8)?\\s*\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$");

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");


    public static void validateUser(String firstName, String lastName, String password,
                              String normalizedPhone, String email, boolean isUpdate)
    {
        validateNameAndPassword(firstName, lastName, password, isUpdate);
        validatePhone(normalizedPhone, isUpdate);
        validateEmail(email, isUpdate);
    }

    private static void validateNameAndPassword(String firstName, String lastName,
                                                String password, boolean isUpdate)
    {
        if (firstName.length() > 2 && lastName.length() > 2) {
            if (!isUpdate || (isUpdate && password != null)) {
                if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    throw new RegistrationValidateException("Incorrect password format");
                }
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

    private static void validatePhone(String normalizedPhone, boolean isUpdate) {
        if (!isUpdate || (isUpdate && normalizedPhone != null)) {
            if (PHONE_PATTERN.matcher(normalizedPhone).matches()) {
                if (userRepository.findByPhone(normalizedPhone).isPresent()) {
                    throw new RegistrationValidateException("The phone is busy");
                }
            } else {
                throw new RegistrationValidateException("Incorrect phone number format");
            }
        }
    }

    private static void validateEmail(String email, boolean isUpdate) {
        if (!isUpdate || (isUpdate && email != null)) {
            if (EMAIL_PATTERN.matcher(email).matches()) {
                if (userRepository.findByEmail(email).isPresent()) {
                    throw new RegistrationValidateException("The email is busy");
                }
            } else {
                throw new RegistrationValidateException("Incorrect email format");
            }
        }
    }
}
