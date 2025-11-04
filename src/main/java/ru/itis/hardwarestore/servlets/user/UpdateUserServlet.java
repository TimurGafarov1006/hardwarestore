package ru.itis.hardwarestore.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.exceptions.user.RegistrationValidateException;
import ru.itis.hardwarestore.exceptions.app.UnauthorizedException;
import ru.itis.hardwarestore.models.User;
import ru.itis.hardwarestore.services.interfaces.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@WebServlet("/cabinet/update")
public class UpdateUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = (UUID) req.getAttribute("userId");
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

        req.getRequestDispatcher("/WEB-INF/views/user/update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User oldUser = userService.getUser((UUID) req.getAttribute("userId"));
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        // если пользак ввел что-то в форму, то будем передавать этот параметр, иначе null
        String password = (req.getParameter("password") != null && !req.getParameter("password").isBlank())
                ? req.getParameter("password")
                : null;
        // если пользак не ввел ничего и в форме остался старый параметр - передаем null, иначе новый параметр
        String phone = (req.getParameter("phone").equals(oldUser.getPhone()))
                ? null
                : req.getParameter("phone");
        String email = (req.getParameter("email").equals(oldUser.getEmail()))
                ? null
                : req.getParameter("email");
        // если пользак не выбрал ДР - то null, иначе либо ДР при регистрации, либо только что введённый
        LocalDate birthday = (req.getParameter("birthday") != null && !req.getParameter("birthday").isBlank())
                ? LocalDate.parse(req.getParameter("birthday"))
                : null;
        //проверка на то, обновил ли пользователь хоть один параметр
        if (! (firstName.equals(oldUser.getFirstName()) &&
                lastName.equals(oldUser.getLastName()) &&
                password == null && phone == null &&
                email == null && birthday != null))
        {
            try {
                userService.updateUser(oldUser, firstName, lastName, password, phone, email, birthday);
            } catch (RegistrationValidateException e) {
                req.setAttribute("error", e.getMessage());
                req.setAttribute("user", oldUser);
                req.getRequestDispatcher("/WEB-INF/views/user/update.jsp").forward(req, resp);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/cabinet");
    }
}
