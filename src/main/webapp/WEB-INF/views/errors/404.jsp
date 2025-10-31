<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Страница не найдена</title>
        <link rel="stylesheet" href="<c:url value='/css/errors/errors.css' />" />
    </head>
<body>
    <div class="error-container">
        <div class="error-card">
            <div class="error-code">404</div>
            <h1 class="error-title">Страница не найдена</h1>
            <p class="error-message">
                Кажется, вы попали по ссылке, которой не существует.<br>
                Не переживайте — это случается со всеми.
            </p>
            <a href="<c:url value='/catalog' />" class="back-link">Вернуться в каталог</a>
        </div>
    </div>
</body>
</html>