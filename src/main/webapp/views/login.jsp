<%--@elvariable id="user" type="database.pojo.User"--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <title>Вход в систему</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<div class="container-fluid" style="margin-top: 50px">
    <div class="row">
        <div class="col-xs-1 col-sm-2 col-md-3 col-lg-4"></div>
        <div class="col-xs-10 col-sm-8 col-md-6 col-lg-4">
            <div class="well">
                <form role="form" action="${pageContext.request.contextPath}/login" method="POST">
                    <h1 class="text-center">Вход</h1>
                    <div class="form-group">
                        <label for="login">Login</label>
                        <input type="text" class="form-control" id="login" placeholder="Введите логин" name="login"
                               value="${user.login}" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Пароль</label>
                        <input type="password" class="form-control" id="password" placeholder="Пароль" name="password"
                               value="${user.password}" required>
                    </div>
                    <div class="checkbox">
                        <label><input type="checkbox" name="remember" value="Y"> Запомнить меня</label>
                    </div>
                    <button type="submit" class="btn btn-success">Войти</button>
                </form>
            </div>
            <jsp:include page="alerts/success.jsp"/>
            <jsp:include page="alerts/error.jsp"/>
        </div>
        <div class="col-xs-1 col-sm-2 col-md-3 col-lg-4"></div>
    </div>
</div>
</body>
</html>
