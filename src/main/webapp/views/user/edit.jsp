<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="user" type="database.pojo.User"--%>
<html>
<head>
    <title>Редактирование пользователя</title>
    <meta charset="UTF-8">
    <!-- Bootstrap -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form role="form" action="${pageContext.request.contextPath}/user/doEdit" method="POST">
        <div class="form-group">
            <label for="id" class="control-label">Логин</label>
            <input type="number" class="form-control" id="id" name="id" value="${user.id}" readonly>
        </div>
        <div class="form-group">
            <label for="login" class="control-label">Логин</label>
            <input type="text" class="form-control" id="login" name="login" placeholder="Введите логин"
                   value="${user.login}">
        </div>
        <div class="form-group">
            <label for="name" class="control-label">Имя</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Введите имя"
                   value="${user.name}">
        </div>
        <div class="form-group">
            <label for="password" class="control-label">Пароль</label>
            <input type="text" class="form-control" id="password" name="password" placeholder="Пароль"
                   value="${user.password}">
        </div>
        <div class="form-group">
            <label for="email" class="control-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Введите e-mail"
                   value="${user.email}">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">Изменить</button>
        </div>
    </form>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="../../js/bootstrap.min.js"></script>
</body>
</html>
