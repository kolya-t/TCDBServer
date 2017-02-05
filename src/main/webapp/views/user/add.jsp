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
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-1 col-sm-2 col-md-3 col-lg-4"></div>
        <div class="col-xs-10 col-sm-8 col-md-6 col-lg-4">
            <div class="well">
                <form role="form" action="${pageContext.request.contextPath}/user/add" method="POST">
                    <div class="form-group">
                        <label for="login" class="control-label">Логин</label>
                        <input type="text" class="form-control" id="login" name="login" placeholder="Введите логин"
                               value="${user.login}" required>
                    </div>
                    <div class="form-group">
                        <label for="password" class="control-label">Пароль</label>
                        <input type="text" class="form-control" id="password" name="password" placeholder="Пароль"
                               value="${user.password}" required>
                    </div>
                    <div class="form-group">
                        <label for="email" class="control-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Введите e-mail"
                               value="${user.email}" required>
                    </div>
                    <div class="form-group">
                        <label for="role" class="control-label">Роль</label>
                        <input type="text" class="form-control" id="role" name="role" placeholder="Введите роль"
                               value="${user.role}" required>
                        <p class="help-block">Например <strong>admin</strong> или <strong>user</strong></p>
                    </div>
                    <div class="form-group text-right">
                        <button type="submit" class="btn btn-primary">Добавить</button>
                    </div>
                </form>
            </div>
            <jsp:include page="../alerts/success.jsp"/>
            <jsp:include page="../alerts/error.jsp"/>
        </div>
        <div class="col-xs-1 col-sm-2 col-md-3 col-lg-4"></div>
    </div>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="../../js/bootstrap.min.js"></script>
</body>
</html>
