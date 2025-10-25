package ru.itis.hardwarestore.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.hardwarestore.repositories.repositoryImpl.SessionRepositoryJdbc;
import ru.itis.hardwarestore.repositories.repositoryImpl.UserRepositoryJdbc;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.SessionRepository;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.UserRepository;
import ru.itis.hardwarestore.services.serviceImpl.AuthServiceImpl;
import ru.itis.hardwarestore.services.serviceInterfaces.AuthService;
import ru.itis.hardwarestore.utils.PropertiesUtil;

import java.time.Duration;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = PropertiesUtil.getProperties();
        Duration sessionDuration = Duration.ofMinutes(Integer.parseInt(properties.getProperty("sessionDuration")));

        UserRepository userRepository = new UserRepositoryJdbc();
        SessionRepository sessionRepository = new SessionRepositoryJdbc();

        AuthService authService = new AuthServiceImpl(userRepository, sessionRepository);

        sce.getServletContext().setAttribute("sessionRepository", sessionRepository);
        sce.getServletContext().setAttribute("sessionDuration", sessionDuration);
        sce.getServletContext().setAttribute("authService", authService);
    }
}
