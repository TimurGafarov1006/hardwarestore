<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/user" %>
<html>
<head>
    <title>Корзина</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/cart.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/navigation.css" />
</head>
<body data-context-path="${pageContext.request.contextPath}">
    <div class="container">
        <t:navigation />

        <div class="user-info-panel">
            <c:if test="${empty cart}">
                <div class="empty-cart">
                    <h4>Корзина пуста</h4>
                </div>
            </c:if>

            <c:if test="${not empty cart}">
                <div class="cart-grid">
                    <c:forEach var="entry" items="${cart}">
                        <c:set var="cartElement" value="${entry.key}" />
                        <c:set var="product" value="${entry.value}" />
                        <div class="cart-item"
                             data-cart-id="${cartElement.id}"
                             data-product-id="${product.id}"
                             data-user-id="${cartElement.userId}">
                            <img src="${pageContext.request.contextPath}${product.imageUrl}"
                                 alt="${product.name}">
                            <div class="item-info">
                                <h3 class="item-name">${product.name}</h3>
                                <p class="item-price">${product.pricePerUnit} ₽</p>
                            </div>
                            <div class="quantity-controls">
                                <button class="decrease">–</button>
                                <span class="quantity-display">${cartElement.quantity}</span>
                                <button class="increase">+</button>
                            </div>
                            <button class="remove-btn">×</button>
                        </div>
                    </c:forEach>
                </div>

                <div class="checkout-form">
                    <form action="${pageContext.request.contextPath}/cabinet/orders" method="POST">
                        <button type="submit">Оформить заказ</button>
                    </form>
                </div>
            </c:if>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/cart-buttons.js"></script>
</body>
</html>