<%@ page contentType="text/html; UTF-8" pageEncoding="cp1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:if test="${action == 'add'}">Добавление нового пользователя</c:if>
        <c:if test="${action == 'edit'}">Редактирование пользователя</c:if>
    </title>
    <link rel="stylesheet" href="styles.css" type="text/css">
</head>
<body>
<table>
    <thead>
    <tr>
        <th>login</th>
        <th>name</th>
        <th>password</th>
        <th>email</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <form action="/database" method="get">
            <td><input type="text" name="login" value="<c:out value="${user.login}"/>"></td>
            <td><input type="text" name="name" value="<c:out value="${user.name}"/>"></td>
            <td><input type="text" name="password" value="<c:out value="${user.password}"/>"></td>
            <td><input type="text" name="email" value="<c:out value="${user.email}"/>"></td>
            <td class="action_td">
                <c:if test="${action == 'add'}">
                    <input type="submit" value="<c:out value="Добавить" />">
                </c:if>
                <c:if test="${action == 'edit'}">
                    <input type="submit" value="<c:out value="Изменить" />">
                </c:if>
            </td>
            <input type="hidden" name="action" value="${action}">
            <input type="hidden" name="id" value="${user.id}">
        </form>
    </tr>
    </tbody>
</table>
</body>
</html>
