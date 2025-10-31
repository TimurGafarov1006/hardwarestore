package ru.itis.hardwarestore.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.exceptions.app.UnauthorizedException;
import ru.itis.hardwarestore.models.User;
import ru.itis.hardwarestore.services.interfaces.UserService;

import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@WebServlet("/cabinet")
public class CabinetServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = (String) req.getAttribute("userId");
        try {
            User user = userService.getUser(userId);
            LocalDate birthday = user.getBirthday();
            Date birthdayAsUtilDate = (birthday != null)
                    ? (Date) Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant())
                    : null;
            req.setAttribute("user", user);
            req.setAttribute("userBirthday", birthdayAsUtilDate);
        } catch (UnauthorizedException e) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }

        req.getRequestDispatcher("/WEB-INF/views/user/cabinet.jsp").forward(req, resp);
    }
}
