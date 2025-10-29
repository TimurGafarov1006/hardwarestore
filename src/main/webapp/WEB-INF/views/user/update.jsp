<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/cart">Корзина</a>

    <c:if test="${not empty error}">
      <div class="error-message">
        <h4>Ошибка обновления данных</h4>
        <p>${error}</p>
      </div>
    </c:if>
    <form action="${pageContext.request.contextPath}/logout" method="post">
      <button type="submit">Выйти из аккаунта</button>
    </form>

    <a href="${pageContext.request.contextPath}/cabinet">Личные данные</a>
    <a href="${pageContext.request.contextPath}/cabinet/history">История заказов</a>
    <a href="${pageContext.request.contextPath}/cabinet/discount-card">Скидочная карта</a>

    <form method="POST" action="${pageContext.request.contextPath}/cabinet/update">
      <label for="first_name">Имя:</label>
      <textarea name="first_name" id="first_name">${user.firstName}</textarea>

      <label for="last_name">Фамилия:</label>
      <textarea name="last_name" id="last_name">${user.lastName}</textarea>

      <label for="password">Новый пароль:</label>
      <input type="password" name="password" id="password"/>

      <label for="phone">Телефон:</label>
      <textarea name="phone" id="phone">${user.phone}</textarea>

      <label for="email">Почта:</label>
      <textarea name="email" id="email">${user.email}</textarea>

      <c:if test="${user.birthday!=null}">
        <label for="birthday">День рождения:</label>
        <input type="date" name="birthday" id="birthday" value="${user.birthday}" readonly>
      </c:if>

      <c:if test="${user.birthday==null}">
        <label for="birthday">День рождения:</label>
        <input type="date" name="birthday" id="birthday" >
      </c:if>

      <button type="submit">Сохранить изменения</button>

    </form>
</body>
</html>
