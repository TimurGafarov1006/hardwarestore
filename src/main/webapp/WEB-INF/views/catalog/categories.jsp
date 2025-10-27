<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${prevCategory!=null}">
        <a href="${pageContext.request.contextPath}/catalog/${prevCategory.slug}">${prevCategory.name}</a>
    </c:if>
    <c:if test="${prevCategory==null}">
        <a href="${pageContext.request.contextPath}/catalog">Каталог</a>
    </c:if>

    <c:forEach var="category" items="${categories}">
        <p>
            <a href="${pageContext.request.contextPath}/catalog/${category.slug}">${category}</a>
        </p>
    </c:forEach>

    <a href="${pageContext.request.contextPath}/cabinet">Личный кабинет</a>
</body>
</html>
