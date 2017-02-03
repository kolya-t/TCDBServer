<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="user" type="database.pojo.User"--%>
<html>
<head>
    <title>Редактирование пользователя</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../css/styles.css" type="text/css">
    <link rel="stylesheet" href="../../css/user.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
</head>
<body>
<form action="${pageContext.request.contextPath}/user/doEdit" method="POST">
    <table>
        <tr>
            <td>
                <label for="id">id</label>
            </td>
            <td>
                <input type="text" id="id" name="id" value="<c:out value="${user.id}"/>" readonly>
            </td>
        </tr>
        <tr>
            <td>
                <label for="login">login</label>
            </td>
            <td>
                <input type="text" id="login" name="login" value="<c:out value="${user.login}"/>">
            </td>
        </tr>
        <tr>
            <td>
                <label for="name">name</label>
            </td>
            <td>
                <input type="text" id="name" name="name" value="<c:out value="${user.name}"/>">
            </td>
        </tr>
        <tr>
            <td>
                <label for="password">password</label>
            </td>
            <td>
                <input type="text" id="password" name="password" value="<c:out value="${user.password}"/>">
            </td>
        </tr>
        <tr>
            <td>
                <label for="email">e-mail</label>
            </td>
            <td>
                <input type="text" id="email" name="email" value="<c:out value="${user.email}"/>">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input type="submit" name="action" value="Редактировать">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
