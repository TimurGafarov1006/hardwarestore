package ru.itis.hardwarestore.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.hardwarestore.repositories.repositoryImpl.SessionRepositoryJdbc;
import ru.itis.hardwarestore.repositories.repositoryImpl.UserRepositoryJdbc;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.SessionRepository;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.UserRepository;
import ru.itis.hardwarestore.services.serviceImpl.SecurityServiceImpl;
import ru.itis.hardwarestore.services.serviceInterfaces.SecurityService;
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

        SecurityService securityService = new SecurityServiceImpl(userRepository, sessionRepository);

        sce.getServletContext().setAttribute("sessionDuration", sessionDuration);
        sce.getServletContext().setAttribute("securityService", securityService);
    }
}
