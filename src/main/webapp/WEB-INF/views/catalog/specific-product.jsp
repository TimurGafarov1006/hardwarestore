<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <p>${product.name}</p>
    <p>${product.description}</p>
    <p>${product.pricePerUnit}</p>
    <img src="${pageContext.request.contextPath}${product.imageUrl}" />

    <a href="${pageContext.request.contextPath}/catalog/${productCategory.slug}">${productCategory.name}</a>

<%--    //TODO сделать корзину--%>
    <form action="${pageContext.request.contextPath}/cart/${product.slug}" method="post">
        <button type="submit">Добавить в корзину</button>
    </form>
</body>
</html>
