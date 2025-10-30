<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/user" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/navigation.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/cabinet.css" />
</head>
<body>
    <div class="container">
        <t:navigation />

        <div class="user-info-panel">
            <h2 class="user-info-title">Привет, ${user.firstName}!</h2>
            <div class="user-info-grid">
                <div><span class="info-label">Имя:</span></div>
                <div><span class="info-value">${user.firstName}</span></div>

                <div><span class="info-label">Фамилия:</span></div>
                <div><span class="info-value">${user.lastName}</span></div>

                <div><span class="info-label">Email:</span></div>
                <div><span class="info-value">${user.email}</span></div>

                <div><span class="info-label">Телефон:</span></div>
                <div><span class="info-value">${user.phone}</span></div>

                <div><span class="info-label">Дата рождения:</span></div>
                <div>
                    <c:choose>
                        <c:when test="${not empty userBirthday}">
                            <span class="info-value">
                                <fmt:formatDate value="${userBirthday}" pattern="dd.MM.yyyy" />
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span class="info-value">Не указана</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
