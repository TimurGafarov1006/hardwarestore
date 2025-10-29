<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:forEach var="order" items="${orders}">
        <p>Заказ под номером ${order.id}</p>
        <p>Стоимость заказа: ${order.totalAmount}Р</p>
    </c:forEach>

    <a href="${pageContext.request.contextPath}/cart">Корзина</a>
    <a href="${pageContext.request.contextPath}/catalog">Каталог</a>
    <a href="${pageContext.request.contextPath}/cabinet/update">Изменить личные данные</a>
    <a href="${pageContext.request.contextPath}/cabinet">Личные данные</a>
    <a href="${pageContext.request.contextPath}/cabinet/discount-card">Скидочная карта</a>
</body>
</html>
