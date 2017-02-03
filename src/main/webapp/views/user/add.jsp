<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="user" type="database.pojo.User"--%>
<html>
<head>
    <title>Создание нового пользователя</title>
    <meta charset="UTF-8">
    <!-- Bootstrap -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form role="form" action="${pageContext.request.contextPath}/user/doAdd" method="POST">
        <div class="form-group">
            <label for="login" class="control-label">Логин</label>
            <input type="text" class="form-control" id="login" name="login" placeholder="Введите логин">
        </div>
        <div class="form-group">
            <label for="name" class="control-label">Имя</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Введите имя">
        </div>
        <div class="form-group">
            <label for="password" class="control-label">Пароль</label>
            <input type="text" class="form-control" id="password" name="password" placeholder="Пароль">
        </div>
        <div class="form-group">
            <label for="email" class="control-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Введите e-mail">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">Добавить</button>
        </div>
    </form>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="../../js/bootstrap.min.js"></script>
</body>
</html>
