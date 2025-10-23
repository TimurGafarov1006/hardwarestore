<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${not empty error}">
        <div class="error-message">
            <h4>Ошибка входа</h4>
            <p>${error}</p>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="login">Логин</label>
        <input type="text" name="login" id="login" placeholder="your@email.com или +79012345678" required/>

        <label for="password">Пароль</label>
        <input type="password" name="password" id="password" placeholder="Введите ваш пароль" required/>

        <button class="submit-btn" type="submit">Войти</button>
    </form>

    <p>Еще нет аккаунта?</p>
    <a href="${pageContext.request.contextPath}/sign-up">Создать аккаунт</a>
</body>
</html>
