<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/user" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/navigation.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/update.css" />
</head>
<body>
    <div class="container">
        <t:navigation />

        <div class="user-info-panel">
            <h2 class="user-info-title">Редактирование профиля</h2>

            <c:if test="${not empty error}">
                <div class="error-message">
                    <strong>Ошибка обновления данных:</strong> ${error}
                </div>
            </c:if>

            <form class="edit-form" method="POST" action="${pageContext.request.contextPath}/cabinet/update">
                <div class="form-group">
                    <label for="first_name">Имя:</label>
                    <textarea name="first_name" id="first_name" maxlength="100">${user.firstName}</textarea>
                </div>

                <div class="form-group">
                    <label for="last_name">Фамилия:</label>
                    <textarea name="last_name" id="last_name" maxlength="100">${user.lastName}</textarea>
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <textarea name="email" id="email" maxlength="255">${user.email}</textarea>
                </div>

                <div class="form-group">
                    <label for="phone">Телефон:</label>
                    <textarea name="phone" id="phone" maxlength="20">${user.phone}</textarea>
                </div>

                <div class="form-group">
                    <label for="password">Новый пароль (оставьте пустым, если не меняете):</label>
                    <input type="password" name="password" id="password" />
                </div>

                <div class="form-group">
                    <label for="birthday">Дата рождения:</label>
                    <c:choose>
                        <c:when test="${not empty userBirthday}">
                            <input type="date" name="birthday" id="birthday"
                                   value="<fmt:formatDate value='${userBirthday}' pattern='yyyy-MM-dd' />"
                                   readonly />
                        </c:when>
                        <c:otherwise>
                            <input type="date" name="birthday" id="birthday" />
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Сохранить изменения</button>
                    <a href="${pageContext.request.contextPath}/cabinet" class="btn btn-secondary">Отмена</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
