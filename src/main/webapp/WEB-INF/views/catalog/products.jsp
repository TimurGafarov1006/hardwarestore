<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body data-context-path="${pageContext.request.contextPath}">
    <div style="position: relative; margin-bottom: 20px;">
        <input type="text" id="searchInput" placeholder="Поиск товаров..." autocomplete="off" style="width: 300px; padding: 6px;" />
        <div id="searchSuggestions"></div>
    </div>

    <a href="${pageContext.request.contextPath}/cart">Корзина</a>

    <a href="${pageContext.request.contextPath}/catalog">Каталог</a>
    <a href="${pageContext.request.contextPath}/catalog/${prevCategory.slug}">${prevCategory.name}</a>

    <c:forEach var="product" items="${products}">
      <a href="${pageContext.request.contextPath}/products/${product.slug}">${product.name}</a>
      <img src="${pageContext.request.contextPath}${product.imageUrl}" />
    </c:forEach>

    <a href="${pageContext.request.contextPath}/cabinet">Личный кабинет</a>

    <script src="${pageContext.request.contextPath}/js/search.js"></script>
</body>
</html>
