<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/login.css" />
</head>
<body>
    <div class="login-container">
        <div class="login-card">
            <h1 class="login-title">Регистрация</h1>

            <c:if test="${not empty error}">
                <div class="error-message">
                    <p>${error}</p>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/sign-up" method="post">
                <div class="form-group">
                    <label for="first_name">Имя</label>
                    <input type="text"
                           id="first_name"
                           name="first_name"
                           placeholder="Иван"
                           value="${param.first_name}"
                           required />
                </div>

                <div class="form-group">
                    <label for="last_name">Фамилия</label>
                    <input type="text"
                           id="last_name"
                           name="last_name"
                           placeholder="Иванов"
                           value="${param.last_name}"
                           required />
                </div>

                <div class="form-group">
                    <label for="email">Электронная почта</label>
                    <input type="email"
                           id="email"
                           name="email"
                           placeholder="your@email.com"
                           value="${param.email}"
                           required />
                </div>

                <div class="form-group">
                    <label for="phone">Телефон</label>
                    <input type="tel"
                           id="phone"
                           name="phone"
                           placeholder="+79012345678"
                           value="${param.phone}"
                           required />
                </div>

                <div class="form-group">
                    <label for="password">Пароль</label>
                    <input type="password"
                           id="password"
                           name="password"
                           placeholder="Не менее 8 символов и 1 заглавной латинской буквы"
                           required />
                </div>

                <div class="form-group">
                    <label for="birthday">Дата рождения</label>
                    <input type="date"
                           id="birthday"
                           name="birthday"
                           value="${param.birthday}" />
                </div>

                <button type="submit" class="submit-btn">Зарегистрироваться</button>
            </form>

            <div class="signup-link">
                <p>Уже есть аккаунт?</p>
                <a href="${pageContext.request.contextPath}/login">Войти</a>
            </div>
        </div>
    </div>
</body>
</html>
