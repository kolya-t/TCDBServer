<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="userList" type="java.util.List<database.pojo.User>"--%>
<html>
<head>
    <meta charset="UTF-8">
    <title>База данных пользователей</title>
    <link rel="stylesheet" href="../css/styles.css" type="text/css">
    <link rel="stylesheet" href="../css/userList.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
</head>
<body>
<table>
    <thead>
    <tr>
        <th>id</th>
        <th>login</th>
        <th>name</th>
        <th>password</th>
        <th>e-mail</th>
        <th colspan="2">Действие</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${userList}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.password}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><a href="/editUser?id=<c:out value="${user.id}"/>">Изменить</a></td>
            <td><a href="/doDeleteUser?id=<c:out value="${user.id}"/>">Удалить</a></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="7"><a href="/addUser">Добавить нового пользователя</a></td>
    </tr>
    </tbody>
</table>
</body>
</html>
