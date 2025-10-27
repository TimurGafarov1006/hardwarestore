package ru.itis.hardwarestore.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.hardwarestore.repositories.repositoryImpl.CategoryRepositoryJdbc;
import ru.itis.hardwarestore.repositories.repositoryImpl.ProductRepositoryJdbc;
import ru.itis.hardwarestore.repositories.repositoryImpl.SessionRepositoryJdbc;
import ru.itis.hardwarestore.repositories.repositoryImpl.UserRepositoryJdbc;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.CategoryRepository;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.ProductRepository;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.SessionRepository;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.UserRepository;
import ru.itis.hardwarestore.services.serviceImpl.AuthServiceImpl;
import ru.itis.hardwarestore.services.serviceImpl.CategoryServiceImpl;
import ru.itis.hardwarestore.services.serviceImpl.ProductServiceImpl;
import ru.itis.hardwarestore.services.serviceImpl.UserServiceImpl;
import ru.itis.hardwarestore.services.serviceInterfaces.AuthService;
import ru.itis.hardwarestore.services.serviceInterfaces.CategoryService;
import ru.itis.hardwarestore.services.serviceInterfaces.ProductService;
import ru.itis.hardwarestore.services.serviceInterfaces.UserService;
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
        CategoryRepository categoryRepository = new CategoryRepositoryJdbc();
        ProductRepository productRepository = new ProductRepositoryJdbc();

        AuthService authService = new AuthServiceImpl(userRepository, sessionRepository);
        UserService userService = new UserServiceImpl(userRepository);
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
        ProductService productService = new ProductServiceImpl(productRepository);

        sce.getServletContext().setAttribute("sessionRepository", sessionRepository);
        sce.getServletContext().setAttribute("sessionDuration", sessionDuration);
        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("authService", authService);
        sce.getServletContext().setAttribute("categoryService", categoryService);
        sce.getServletContext().setAttribute("productService", productService);
    }
}
