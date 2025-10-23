package ru.itis.hardwarestore.servlets.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.exceptions.RegistrationValidateException;
import ru.itis.hardwarestore.services.serviceInterfaces.SecurityService;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {
    private SecurityService securityService;
    private Duration sessionDuration;

    @Override
    public void init() throws ServletException {
        this.securityService = (SecurityService) getServletContext().getAttribute("securityService");
        this.sessionDuration = (Duration) getServletContext().getAttribute("sessionDuration");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/security/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        LocalDate birthday = (req.getParameter("birthday") != null && !req.getParameter("birthday").isBlank())
                ? LocalDate.parse(req.getParameter("birthday")) : null;

        String sessionId = null;

        try {
            sessionId = securityService.registerUser(firstName, lastName, password, phone, email, birthday);
        } catch (RegistrationValidateException e) {
            //TODO сделать живую валидацию через js
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/security/registration.jsp").forward(req, resp);
        }

        createCookie(req, resp, sessionId);
        resp.sendRedirect(req.getContextPath() + "/"); //TODO сделать главную страницу сайта
    }

    private void createCookie(HttpServletRequest req, HttpServletResponse resp, String sessionId) {
        Cookie cookie = new Cookie("session_id", sessionId);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(sessionDuration.toMinutesPart() * 60);
        resp.addCookie(cookie);
    }
}
