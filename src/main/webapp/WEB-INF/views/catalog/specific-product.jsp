<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body data-context-path="${pageContext.request.contextPath}">
    <a href="${pageContext.request.contextPath}/cart">Корзина</a>

    <p>${product.name}</p>
    <p>${product.description}</p>
    <p>${product.pricePerUnit}</p>
    <img src="${pageContext.request.contextPath}${product.imageUrl}" />

    <a href="${pageContext.request.contextPath}/catalog/${productCategory.slug}">${productCategory.name}</a>

<%--    //TODO сделать корзину--%>
    <c:if test="${isProductInCart==false}">
        <div class="add-to-cart-button" data-product-id="${product.id}" data-user-id="${user.id}">
            <button type="button" id="addToCartBtn">Добавить в корзину</button>
        </div>
    </c:if>
    <c:if test="${isProductInCart==true}">
        <p>Продукт уже в корзине</p>
    </c:if>

    <script src="${pageContext.request.contextPath}/js/add-to-cart.js"></script>
</body>
</html>
