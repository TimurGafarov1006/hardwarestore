<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/user" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/discount-card.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/navigation.css" />
</head>
<body>
    <div class="container">
        <t:navigation />

        <div class="user-info-panel">
            <h2 class="user-info-title">Скидочная карта</h2>

            <c:choose>
                <c:when test="${discountCard == null}">
                    <p class="no-card-message">У вас пока нет скидочной карты.</p>
                    <form action="${pageContext.request.contextPath}/cabinet/discount-card" method="post">
                        <button type="submit" class="open-card-btn">Открыть скидочную карту</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="discount-card ${discountCard.cardType.name.toLowerCase()}">
                        <div>${discountCard.cardType.name}</div>
                        <div class="card-number">${discountCard.cardNo}</div>
                        <div class="card-discount">Скидка: ${discountCard.cardType.discountPercent}%</div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>
