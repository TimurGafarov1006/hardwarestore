<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/user" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
  <title>Заказ №${order.id}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/cart.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/navigation.css" />
</head>
<body data-context-path="${pageContext.request.contextPath}">
  <div class="container">
    <t:navigation />

    <div class="user-info-panel">
      <h2 class="user-info-title">Заказ №${order.id}</h2>

      <c:if test="${empty products}">
        <div class="empty-cart">
          <h4>В заказе нет товаров</h4>
        </div>
      </c:if>

      <c:if test="${not empty products}">
        <div class="cart-grid">
          <c:forEach var="entry" items="${products}">
            <c:set var="product" value="${entry.key}" />
            <c:set var="quantity" value="${entry.value}" />
            <div class="cart-item">
              <img src="${pageContext.request.contextPath}${product.imageUrl}" alt="${product.name}">
              <div class="item-info">
                <h3 class="item-name">${product.name}</h3>
                <p class="item-price">Цена за шт: <fmt:formatNumber value="${product.pricePerUnit}" pattern="#,##0.00"/> ₽</p>
                <p class="item-subtotal">Итого: <fmt:formatNumber value="${product.pricePerUnit * quantity}" pattern="#,##0.00"/> ₽</p>
              </div>
              <div class="quantity-display">× ${quantity}</div>
            </div>
          </c:forEach>
        </div>

        <div class="order-total">
          <p>Сумма до скидки: <strong><fmt:formatNumber value="${order.amountBeforeDiscount}" pattern="#,##0.00"/> ₽</strong></p>
          <c:if test="${order.discountAmount > 0}">
            <p>Скидка: <strong>–<fmt:formatNumber value="${order.discountAmount}" pattern="#,##0.00"/> ₽</strong></p>
          </c:if>
          <h3>Итого к оплате:
            <span class="total-amount">
                              <fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/> ₽
                          </span>
          </h3>
        </div>
      </c:if>
    </div>
  </div>
</body>
</html>