<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${not empty error}">
        <div class="error-message">
            <h4>Ошибка регистрации</h4>
            <p>${error}</p>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/sign-up" method="post">
        <label for="first_name">Имя</label>
        <input type="text" name="first_name" id="first_name" placeholder="Иван" required value="${param.first_name}"/>

        <label for="last_name">Фамилия</label>
        <input type="text" name="last_name" id="last_name" placeholder="Иванов" required value="${param.last_name}"/>

        <label for="password">Пароль</label>
        <input type="password" name="password" id="password" placeholder="Не менее 8 символов и 1 заглавной латинской буквы" required/>

        <label for="phone">Телефон</label>
        <input type="tel" name="phone" id="phone" placeholder="+79012345678" required value="${param.phone}"/>

        <label for="email">Электронная почта</label>
        <input type="email" name="email" id="email" placeholder="your@email.com" required value="${param.email}"/>

        <label for="birthday">Дата рождения</label>
        <input type="date" name="birthday" id="birthday"/>

        <button type="submit">Зарегистрироваться</button>
    </form>
</body>
</html>
