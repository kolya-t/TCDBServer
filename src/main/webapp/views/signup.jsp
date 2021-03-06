<%--@elvariable id="user" type="database.pojo.User"--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- Bootstrap -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <title>Регистрация</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<div class="container" style="margin-top: 70px;">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Регистрация</h3>
                </div>
                <div class="panel-body">
                    <form role="form" action="${pageContext.request.contextPath}/signup" method="post">
                        <div class="form-group">
                            <input class="form-control" placeholder="Login" name="login" type="text"
                                   value="${user.login}" autofocus>
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Пароль" name="password" type="text"
                                   value="${user.password}">
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="E-Mail" name="email" type="email"
                                   value="${user.email}">
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Role" name="role" type="text" readonly
                                   value="user">
                            <p class="help-block text-info">Вы можете зарегистрироваться только как <strong>user</strong></p>
                        </div>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Создать</button>
                    </form>
                </div>
                <div class="panel-footer">
                    <a href="${pageContext.request.contextPath}/login">Уже зарегистрированы?</a>
                </div>
            </div>
            <jsp:include page="alerts/success.jsp"/>
            <jsp:include page="alerts/error.jsp"/>
        </div>
    </div>
</div>
<c:remove var="user"/>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
