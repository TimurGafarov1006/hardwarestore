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
            <h1 class="login-title">Вход</h1>

            <c:if test="${not empty error}">
                <div class="error-message">
                    <p>${error}</p>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label for="login">Логин</label>
                    <input type="text"
                           id="login"
                           name="login"
                           placeholder="your@email.com или +79012345678"
                           required />
                </div>

                <div class="form-group">
                    <label for="password">Пароль</label>
                    <input type="password"
                           id="password"
                           name="password"
                           placeholder="Введите ваш пароль"
                           required />
                </div>

                <button type="submit" class="submit-btn">Войти</button>
            </form>

            <div class="signup-link">
                <p>Еще нет аккаунта?</p>
                <a href="${pageContext.request.contextPath}/sign-up">Создать аккаунт</a>
            </div>
        </div>
    </div>
</body>
</html>
