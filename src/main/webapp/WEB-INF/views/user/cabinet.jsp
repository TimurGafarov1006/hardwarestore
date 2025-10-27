<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <button type="submit">Выйти из аккаунта</button>
    </form>

    <a href="${pageContext.request.contextPath}/cabinet/update">Изменить личные данные</a>
    <a href="${pageContext.request.contextPath}/cabinet/history">История заказов</a>
    <a href="${pageContext.request.contextPath}/cabinet/discount-card">Скидочная карта</a>

    <p>${user.firstName}</p>
    <p>${user.lastName}</p>
    <p>${user.email}</p>
    <p>${user.phone}</p>

    <a href="${pageContext.request.contextPath}/catalog">Каталог</a>
</body>
</html>
