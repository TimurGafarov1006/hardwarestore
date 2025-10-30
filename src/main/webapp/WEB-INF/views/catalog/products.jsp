<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/catalog" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalog/navigation.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalog/product.css" />
</head>
<body data-context-path="${pageContext.request.contextPath}">
    <t:navigation contextPath="${pageContext.request.contextPath}" prevCategory="${prevCategory}"/>

    <c:forEach var="product" items="${products}">
        <t:product isCatalogPage="${true}" product="${product}" />
    </c:forEach>

    <script src="${pageContext.request.contextPath}/js/search.js"></script>
</body>
</html>
