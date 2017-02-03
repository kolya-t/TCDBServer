<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="user" type="database.pojo.User"--%>
<html>
<head>
    <title>Создание нового пользователя</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../css/styles.css" type="text/css">
    <link rel="stylesheet" href="../../css/user.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
</head>
<body>
<form action="${pageContext.request.contextPath}/user/doAdd" method="POST">
    <table>
        <tr>
            <td>
                <label for="login">login</label>
            </td>
            <td>
                <input type="text" id="login" name="login">
            </td>
        </tr>
        <tr>
            <td>
                <label for="name">name</label>
            </td>
            <td>
                <input type="text" id="name" name="name">
            </td>
        </tr>
        <tr>
            <td>
                <label for="password">password</label>
            </td>
            <td>
                <input type="text" id="password" name="password">
            </td>
        </tr>
        <tr>
            <td>
                <label for="email">e-mail</label>
            </td>
            <td>
                <input type="text" id="email" name="email">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input type="submit" name="action" value="Добавить">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
