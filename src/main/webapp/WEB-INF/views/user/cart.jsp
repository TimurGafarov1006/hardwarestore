<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body data-context-path="${pageContext.request.contextPath}">
    <c:if test="${empty cart}">
        <div class="empty-cart">
            <h4>Корзина пуста</h4>
        </div>
    </c:if>

    <a href="${pageContext.request.contextPath}/catalog">Каталог</a>
    <a href="${pageContext.request.contextPath}/cabinet">Личный кабинет</a>

    <c:forEach var="cartElement" items="${cart}">
        <p>Key: ${cartElement.value}</p>
        <div class="cart-item" data-cart-id="${cartElement.key.id}" data-product-id="${cartElement.key.id}" data-user-id="${cartElement.key.userId}">
            <span class="quantity">${cartElement.key.quantity}</span>
            <button class="increase">+</button>
            <button class="decrease">–</button>
            <button class="remove">×</button>
        </div>
    </c:forEach>

    <form action="${pageContext.request.contextPath}/cabinet/orders" method="POST">
        <button type="submit">Оформить заказ</button>
    </form>

    <script src="${pageContext.request.contextPath}/js/cart-buttons.js"></script>
</body>
</html>
