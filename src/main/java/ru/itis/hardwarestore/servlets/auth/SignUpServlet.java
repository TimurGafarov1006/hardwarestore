package ru.itis.hardwarestore.servlets.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.exceptions.RegistrationValidateException;
import ru.itis.hardwarestore.services.serviceInterfaces.AuthService;
import ru.itis.hardwarestore.utils.CookieUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {
    private AuthService authService;
    private Duration sessionDuration;

    @Override
    public void init() throws ServletException {
        this.authService = (AuthService) getServletContext().getAttribute("authService");
        this.sessionDuration = (Duration) getServletContext().getAttribute("sessionDuration");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/auth/registration.jsp").forward(req, resp);
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
            sessionId = authService.registerUser(firstName, lastName, password, phone, email, birthday);
        } catch (RegistrationValidateException e) {
            //TODO сделать живую валидацию через js
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/auth/registration.jsp").forward(req, resp);
        }

        CookieUtils.createCookie(req, resp, sessionId, sessionDuration);
        resp.sendRedirect(req.getContextPath() + "/"); //TODO сделать главную страницу сайта
    }
}
