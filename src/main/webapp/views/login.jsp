<%--@elvariable id="user" type="database.pojo.User"--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Вход в систему</title>
    <!-- Bootstrap -->
    <!-- Bootstrap -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
</head>
<body>
<jsp:include page="_header.jsp"/>
<div class="container" style="margin-top: 70px;">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">Вход в систему</h3>
                </div>
                <div class="panel-body">
                    <form role="form" action="${pageContext.request.contextPath}/login" method="post">
                        <div class="form-group">
                            <input class="form-control" placeholder="Login" name="login" type="text"
                                   value="${user.login}" autofocus>
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Пароль" name="password" type="password"
                                   value="${user.password}">
                        </div>
                        <div class="checkbox">
                            <label><input name="remember" type="checkbox" value="Y"> Запомнить меня</label>
                        </div>
                        <button class="btn btn-lg btn-success btn-block" type="submit">Войти</button>
                    </form>
                </div>
                <div class="panel-footer">
                    <a href="${pageContext.request.contextPath}/signup">Еще не зарегистрированы?</a>
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
