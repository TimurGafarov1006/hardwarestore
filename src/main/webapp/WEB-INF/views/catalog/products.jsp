<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:forEach var="product" items="${products}">
      <a href="${pageContext.request.contextPath}/products/${product.slug}">${product.name}</a>
      <p>${product.description}</p>
      <p>${product.pricePerUnit}</p>
      <img src="${pageContext.request.contextPath}${product.imageUrl}" />
    </c:forEach>

    <a href="${pageContext.request.contextPath}/cabinet">Личный кабинет</a>
</body>
</html>
