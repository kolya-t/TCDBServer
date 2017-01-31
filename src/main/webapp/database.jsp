<%@ page contentType="text/html; UTF-8" pageEncoding="cp1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>”правление базой данных пользователей</title>
    <link rel="stylesheet" href="styles.css" type="text/css">
</head>
<body>
<table>
    <thead>
    <tr>
        <th>id</th>
        <th>login</th>
        <th>name</th>
        <th>password</th>
        <th>email</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.password}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td class="action_td">
                <a href="/inputUserData?action=edit&id=<c:out value="${user.id}"/>">
                    <img src="img/edit.png" height="32px">
                </a>
                <a href="/database?action=delete&id=<c:out value="${user.id}"/>">
                    <img src="img/delete.png" height="32px">
                </a>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="5" class="invisible_td"></td>
        <td class="action_td">
            <a href="/inputUserData?action=add&id=<c:out value="${user.id}"/>">
                <img src="img/add.png" height="32px">
            </a>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>