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
</body>
</html>
