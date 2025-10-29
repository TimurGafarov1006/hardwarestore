<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/cart">Корзина</a>

    <a href="${pageContext.request.contextPath}/cabinet/update">Изменить личные данные</a>
    <a href="${pageContext.request.contextPath}/cabinet/orders">История заказов</a>
    <a href="${pageContext.request.contextPath}/cabinet">Личные данные</a>

    <c:if test="${discountCard==null}">
        <p>У вас нет скидочной карты. Давайте откроем!</p>

        <form action="${pageContext.request.contextPath}/cabinet/discount-card" method="post">
            <button type="submit">Открыть скидочную карту</button>
        </form>
    </c:if>
    <c:if test="${discountCard!=null}">
        <p>${discountCard.cardType.name}</p>
        <p>${discountCard.cardNo}</p>
        <p>${discountCard.cardType.discountPercent}%</p>
    </c:if>

    <a href="${pageContext.request.contextPath}/catalog">Каталог</a>
</body>
</html>
