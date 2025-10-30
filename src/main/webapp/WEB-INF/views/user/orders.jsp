<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/user" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/navigation.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/orders.css" />
</head>
<body>
    <div class="container">
        <t:navigation />

        <div class="user-info-panel">
            <h2 class="user-info-title">История заказов</h2>

            <c:choose>
                <c:when test="${empty orders}">
                    <p class="no-orders-message">У вас пока нет заказов.</p>
                </c:when>
                <c:otherwise>
                    <div class="orders-list">
                        <c:forEach var="order" items="${orders}">
                            <div class="order-card">
                                <div class="order-header">
                                    <a href="${pageContext.request.contextPath}/cabinet/orders/${order.id}"><span class="order-id">Заказ №${order.id}</span></a>
                                    <span class="order-date">
                                            <fmt:formatDate value="${order.createdAt}" pattern="dd.MM.yyyy HH:mm" />
                                        </span>
                                </div>
                                <div class="order-details">
                                    <div><span class="detail-label">Сумма до скидки:</span> <span class="detail-value">${order.amountBeforeDiscount} ₽</span></div>
                                    <div><span class="detail-label">Скидка:</span> <span class="detail-value">${order.discountAmount} ₽</span></div>
                                    <div><span class="detail-label">Итого:</span> <span class="detail-value total-amount">${order.totalAmount} ₽</span></div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>
