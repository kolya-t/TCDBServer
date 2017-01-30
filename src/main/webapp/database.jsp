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
            <td><c:out value="${user.id}" ></c:out></td>
            <td><c:out value="${user.login}" ></c:out></td>
            <td><c:out value="${user.name}" ></c:out></td>
            <td><c:out value="${user.password}" ></c:out></td>
            <td><c:out value="${user.email}" ></c:out></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>