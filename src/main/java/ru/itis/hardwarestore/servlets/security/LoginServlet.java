package ru.itis.hardwarestore.servlets.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.exceptions.LoginValidateException;
import ru.itis.hardwarestore.services.serviceInterfaces.SecurityService;
import ru.itis.hardwarestore.utils.CookieUtils;

import java.io.IOException;
import java.time.Duration;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private SecurityService securityService;
    private Duration sessionDuration;

    @Override
    public void init() throws ServletException {
        this.securityService = (SecurityService) getServletContext().getAttribute("securityService");
        this.sessionDuration = (Duration) getServletContext().getAttribute("sessionDuration");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/security/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        String sessionId = null;

        try {
            sessionId = securityService.loginUser(login, password);
        } catch (LoginValidateException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/security/login.jsp").forward(req, resp);
        }

        CookieUtils.createCookie(req, resp, sessionId, sessionDuration);
        resp.sendRedirect(req.getContextPath() + "/"); //TODO создать главную страницу
    }
}
